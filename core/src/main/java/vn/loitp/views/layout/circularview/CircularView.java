package vn.loitp.views.layout.circularview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import java.util.ArrayList;

import loitp.core.R;

/**
 * TODO: document your custom view class.
 */
public class CircularView extends View {
    private static final String TAG = CircularView.class.getSimpleName();
    private String mText; //TODO add customization for the text (style, color, etc)

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private Paint mCirclePaint;
    private static final float CIRCLE_WEIGHT_LONG_ORIENTATION = 0.9f;
    private static final float CIRCLE_TO_MARKER_PADDING = 20f;
    private final float BASE_MARKER_RADIUS = 40;
    private int mDefaultMarkerRadius = (int) BASE_MARKER_RADIUS;
    private float mMarkerStartingPoint;

    private BaseCircularViewAdapter mAdapter;

    private final AdapterDataSetObserver mAdapterDataSetObserver = new AdapterDataSetObserver();
    private OnClickListener mOnCircularViewObjectClickListener;
    private OnHighlightAnimationEndListener mOnHighlightAnimationEndListener;

    private ArrayList<Marker> mMarkerList;
    private CircularViewObject mCircle;
    private float mHighlightedDegree;
    private Marker mHighlightedMarker;
    private int mHighlightedMarkerPosition;
    private boolean mDrawHighlightedMarkerOnTop;
    /**
     * Use this to specify that no degree should be highlighted.
     */
    public static final float HIGHLIGHT_NONE = Float.MIN_VALUE;
    private boolean mAnimateMarkersOnStillHighlight;
    private boolean mAnimateMarkersOnHighlightAnimation;
    private boolean mIsAnimating;
    private ObjectAnimator mHighlightedDegreeObjectAnimator;

    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;

    private int mWidth;
    private int mHeight;

    public static final int TOP = 270;
    public static final int BOTTOM = 90;
    public static final int LEFT = 180;
    public static final int RIGHT = 0;

    private int mEditModeMarkerCount;
    private int mEditModeMarkerRadius;

    public CircularView(Context context) {
        super(context);
        init(null, 0);
    }

    public CircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircularView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircularView, defStyle, 0);
        final int centerBackgroundColor = a.getColor(
                R.styleable.CircularView_centerBackgroundColor,
                CircularViewObject.NO_COLOR);

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        mText = a.getString(R.styleable.CircularView_text_circular);
        mTextPaint.setTextSize(a.getDimension(
                R.styleable.CircularView_textSize_circular,
                24f));
        mTextPaint.setColor(a.getColor(
                R.styleable.CircularView_textColor_circular,
                mTextPaint.getColor()));


        Drawable circleDrawable = null;
        if (a.hasValue(R.styleable.CircularView_centerDrawable)) {
            circleDrawable = a.getDrawable(
                    R.styleable.CircularView_centerDrawable);
            circleDrawable.setCallback(this);
        }

        mHighlightedDegreeObjectAnimator = new ObjectAnimator();
        mHighlightedDegreeObjectAnimator.setTarget(CircularView.this);
        mHighlightedDegreeObjectAnimator.setPropertyName("highlightedDegree");
        mHighlightedDegreeObjectAnimator.addListener(mAnimatorListener);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        mCirclePaint = new Paint();
        mCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);

        mDrawHighlightedMarkerOnTop = a.getBoolean(R.styleable.CircularView_drawHighlightedMarkerOnTop, false);
        mHighlightedMarker = null;
        mHighlightedMarkerPosition = -1;
        mHighlightedDegree = a.getFloat(R.styleable.CircularView_highlightedDegree, HIGHLIGHT_NONE);
        mMarkerStartingPoint = a.getFloat(R.styleable.CircularView_markerStartingPoint, 0f);
        mAnimateMarkersOnStillHighlight = a.getBoolean(R.styleable.CircularView_animateMarkersOnStillHighlight, false);
        mAnimateMarkersOnHighlightAnimation = false;
        mIsAnimating = false;

        mCircle = new CircularViewObject(getContext(), CIRCLE_TO_MARKER_PADDING, centerBackgroundColor);
        mCircle.setSrc(circleDrawable);
        mCircle.setFitToCircle(a.getBoolean(R.styleable.CircularView_fitToCircle, false));

        mDefaultMarkerRadius = getResources().getInteger(R.integer.cv_default_marker_radius);

        mEditModeMarkerCount = a.getInt(R.styleable.CircularView_editMode_markerCount, 0);
        mEditModeMarkerRadius = a.getInt(R.styleable.CircularView_editMode_markerRadius, mDefaultMarkerRadius);

        a.recycle();

        setOnLongClickListener(mOnLongClickListener);

        if (isInEditMode()) {
            mAdapter = new SimpleCircularViewAdapter() {
                @Override
                public int getCount() {
                    return mEditModeMarkerCount;
                }

                @Override
                public void setupMarker(int position, Marker marker) {
                    marker.setRadius(mEditModeMarkerRadius);
                    marker.setCenterBackgroundColor(getResources().getColor(android.R.color.black));
                }
            };
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        // init circle dimens
        final int shortDimension = Math.min(
                mWidth = super.getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                mHeight = super.getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        final float actualDimension = Math.round(shortDimension * CIRCLE_WEIGHT_LONG_ORIENTATION);
        final float circleRadius = (actualDimension - BASE_MARKER_RADIUS * 4f - CIRCLE_TO_MARKER_PADDING * 2f) / 2f;
        final float circleCenterX = mWidth / 2f;
        final float circleCenterY = mHeight / 2f;
        mCircle.init(circleCenterX, circleCenterY, circleRadius, mAdapterDataSetObserver);

        setMeasuredDimension(getDefaultSize((int) Math.ceil(actualDimension), widthMeasureSpec),
                getDefaultSize((int) Math.ceil(actualDimension), heightMeasureSpec));
    }

    /**
     * Utility to return a default size. Uses the supplied size if the
     * MeasureSpec imposed no constraints. Will get larger if allowed
     * by the MeasureSpec.
     *
     * @param size        Default size for this view
     * @param measureSpec Constraints imposed by the parent
     * @return The size this view should be.
     */
    public static int getDefaultSize(final int size, final int measureSpec) {
        final int result;
        final int specMode = MeasureSpec.getMode(measureSpec);
        final int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            default:
                result = size;
                break;
        }
        return result;
    }

    /**
     * Make sure a degree value is less than or equal to 360 and greater than or equal to 0.
     *
     * @param degree Degree to normalize
     * @return Return a positive degree value
     */
    private float normalizeDegree(float degree) {
        if (degree < 0f) {
            degree = 360f + degree;
        }
        return degree % 360f;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        setupMarkerList();
    }

    private void setupMarkerList() {
        if (mAdapter != null) {
            // init marker dimens
            final int markerCount = mAdapter.getCount();
            assert (markerCount >= 0);
            if (mMarkerList == null) {
                mMarkerList = new ArrayList<Marker>(markerCount);
            }
            int markerViewListSize = mMarkerList.size();
            final float degreeInterval = 360.0f / markerCount;
            final float radiusFromCenter = mCircle.getRadius() + CIRCLE_TO_MARKER_PADDING + BASE_MARKER_RADIUS;
            int position = 0;
            float degree = mMarkerStartingPoint;
            // loop clockwise
            for (; position < markerCount; position++) {
                final boolean positionHasExistingMarkerInList = position < markerViewListSize;
                final float actualDegree = normalizeDegree(degree);
                final double rad = Math.toRadians(actualDegree);
                final float sectionMin = actualDegree - degreeInterval / 2f;

                // get the old marker view if it exists.
                final Marker newMarker;
                if (positionHasExistingMarkerInList) {
                    newMarker = mMarkerList.get(position);
                } else {
                    newMarker = new Marker(getContext());
                    mMarkerList.add(newMarker);
                }

                // Initialize all other necessary values
                newMarker.init(
                        (float) (radiusFromCenter * Math.cos(rad)) + mCircle.getX(),
                        (float) (radiusFromCenter * Math.sin(rad)) + mCircle.getY(),
                        mDefaultMarkerRadius,
                        normalizeDegree(sectionMin),
                        normalizeDegree(sectionMin + degreeInterval) - 0.001f,
                        mAdapterDataSetObserver);
                newMarker.setShouldAnimateWhenHighlighted(mAnimateMarkersOnStillHighlight);

                // get the new marker view.
                mAdapter.setupMarker(position, newMarker);

                // Make sure it's drawable has the callback set
                newMarker.setCallback(this);

                degree += degreeInterval;
            }
            // Remove extra markers that aren't used in this list anymore.
            markerViewListSize = mMarkerList.size();
            for (; position < markerViewListSize; position++) {
                mMarkerList.remove(position--);
                markerViewListSize--;
            }
            mMarkerList.trimToSize();
            // Force any effect of highlighting.
        }
        // Workaround. Setting the state of a drawable immediately doesn't seem to update correctly.
        // Delaying the action works.
        postDelayed(setCurrentHighlightedDegree, 5);
    }

    private final Runnable setCurrentHighlightedDegree = new Runnable() {
        @Override
        public void run() {
            setHighlightedDegree(mHighlightedDegree);
        }
    };

    private void invalidateTextPaintAndMeasurements() {
        mTextWidth = mText == null ? 0 : mTextPaint.measureText(mText);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    //TODO always draw the animating markers on top.
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int contentWidth = mWidth - paddingLeft - paddingRight;
        int contentHeight = mHeight - paddingTop - paddingBottom;

        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);
        // Draw CircularViewObject
        mCircle.draw(canvas);
        // Draw non-highlighted Markers
        if (mMarkerList != null && !mMarkerList.isEmpty()) {
            for (final Marker marker : mMarkerList) {
                if (!mDrawHighlightedMarkerOnTop || !marker.equals(mHighlightedMarker)) {
                    marker.draw(canvas);
                }
            }
        }

        // Draw highlighted marker
        if (mDrawHighlightedMarkerOnTop && mHighlightedMarker != null) {
            mHighlightedMarker.draw(canvas);
        }

        // Draw line
        if (mIsAnimating) {
            final float radiusFromCenter = mCircle.getRadius() + CIRCLE_TO_MARKER_PADDING + BASE_MARKER_RADIUS;
            final float x = (float) Math.cos(Math.toRadians(mHighlightedDegree)) * radiusFromCenter + mCircle.getX();
            final float y = (float) Math.sin(Math.toRadians(mHighlightedDegree)) * radiusFromCenter + mCircle.getY();
            canvas.drawLine(mCircle.getX(), mCircle.getY(), x, y, mCirclePaint);
        }


        // Draw the text.
        if (!TextUtils.isEmpty(mText)) {
            canvas.drawText(mText,
                    mCircle.getX() - mTextWidth / 2f,
                    mCircle.getY() - mTextHeight / 2f,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
                    mTextPaint);
        }
    }

    /**
     * Set the adapter to use on this view.
     *
     * @param adapter Adapter to set.
     */
    public void setAdapter(final BaseCircularViewAdapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mAdapterDataSetObserver);
        }
        postInvalidate();
    }

    /**
     * Get the adapter that has been set on this view.
     * See #setAdapter(BaseCircularViewAdapter)
     *
     * @return The adapter that has been set on this view.
     */
    public BaseCircularViewAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * Gets the text for this view.
     * See R.styleable#CircularView_text
     *
     * @return The text for this view.
     */
    public String getText() {
        return mText;
    }

    /**
     * Sets the view's text.
     * See R.styleable#CircularView_text
     *
     * @param text The view's text.
     */
    public void setText(String text) {
        mText = text;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     * See R.styleable#CircularView_textSize
     *
     * @return The example dimension attribute value.
     */
    public float getTextSize() {
        return mTextPaint.getTextSize();
    }

    /**
     * Set the default text size to the given value, interpreted as "scaled
     * pixel" units.  This size is adjusted based on the current density and
     * user font size preference.
     * See R.styleable#CircularView_textSize
     *
     * @param size The scaled pixel size.
     */
    public void setTextSize(float size) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }

    /**
     * Set the default text size to a given unit and value.  See {@link
     * TypedValue} for the possible dimension units.
     * See R.styleable#CircularView_textSize
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setTextSize(int unit, float size) {
        Context c = getContext();
        Resources r;

        if (c == null)
            r = Resources.getSystem();
        else
            r = c.getResources();

        setRawTextSize(TypedValue.applyDimension(
                unit, size, r.getDisplayMetrics()));
    }

    private void setRawTextSize(float size) {
        if (size != mTextPaint.getTextSize()) {
            mTextPaint.setTextSize(size);
            invalidateTextPaintAndMeasurements();
            postInvalidate();
        }
    }

    /**
     * Set the paint's color. Note that the color is an int containing alpha
     * as well as r,g,b. This 32bit value is not premultiplied, meaning that
     * its alpha can be any value, regardless of the values of r,g,b.
     * See the Color class for more details.
     * See R.styleable#CircularView_textColor
     *
     * @param color The new color (including alpha) to set for the text.
     */
    public void setTextColor(int color) {
        if (mTextPaint.getColor() != color) {
            mTextPaint.setColor(color);
            postInvalidate();
        }
    }

    /**
     * /**
     * Return the paint's color. Note that the color is a 32bit value
     * containing alpha as well as r,g,b. This 32bit value is not premultiplied,
     * meaning that its alpha can be any value, regardless of the values of
     * r,g,b. See the Color class for more details.
     * See R.styleable#CircularView_textColor
     *
     * @return the text's color (and alpha).
     */
    public int getTextColor() {
        return mTextPaint.getColor();
    }

    /**
     * Get the degree that is currently highlighted.
     * See R.styleable#CircularView_highlightedDegree
     *
     * @return The highlighted degree
     */
    public float getHighlightedDegree() {
        return mHighlightedDegree;
    }

    /**
     * Set the degree that will trigger highlighting a marker. You can also set {@link #HIGHLIGHT_NONE} to not highlight any degree.
     * See R.styleable#CircularView_highlightedDegree
     *
     * @param highlightedDegree Value in degrees.
     */
    public void setHighlightedDegree(final float highlightedDegree) {
        this.mHighlightedDegree = highlightedDegree;

        mHighlightedMarker = null;
        mHighlightedMarkerPosition = -1;
        // Loop through all markers to see if any of them are highlighted.
        if (mMarkerList != null) {
            final int size = mMarkerList.size();
            for (int i = 0; i < size; i++) {
                final Marker marker = mMarkerList.get(i);
                // Only check the marker if the visibility is not "gone"
                if (marker.getVisibility() != View.GONE) {
                    final boolean markerIsHighlighted = mHighlightedDegree != HIGHLIGHT_NONE && marker.hasInSection(mHighlightedDegree % 360);
                    marker.setHighlighted(markerIsHighlighted);
                    if (markerIsHighlighted) {
                        // Marker is highlighted!
                        mHighlightedMarker = marker;
                        mHighlightedMarkerPosition = i;
                        final boolean highlightAnimationAndAnimateMarker = mIsAnimating && mAnimateMarkersOnHighlightAnimation;
                        final boolean stillAndAnimateMarker = !mIsAnimating && mAnimateMarkersOnStillHighlight;
                        final boolean wantsToAnimateMarker = highlightAnimationAndAnimateMarker || stillAndAnimateMarker;
                        // Animate only if necessary
                        if (wantsToAnimateMarker && !marker.isAnimating()) {
                            marker.animateBounce();
                        }
                    }
                }
                // Continue looping through the rest to reset other markers.
            }
        }
        postInvalidate();
    }

    /**
     * Check if a marker should animate when it is highlighted. By default this is false and when it is
     * set to true the marker will constantly be animating.
     * See R.styleable#CircularView_animateMarkersOnStillHighlight
     *
     * @return True if a marker should animate when it is highlighted, false if not.
     * @see #setHighlightedDegree(float)
     */
    public boolean isAnimateMarkerOnStillHighlight() {
        return mAnimateMarkersOnStillHighlight;
    }

    /**
     * If set to true the marker that is highlighted with {@link #setHighlightedDegree(float)} will
     * animate continuously when the highlight degree is not animating. This is set to false by default.
     * See R.styleable#CircularView_animateMarkersOnStillHighlight
     *
     * @param animateMarkerOnHighlight True to continuously animate, false to turn it off.
     */
    public void setAnimateMarkerOnStillHighlight(boolean animateMarkerOnHighlight) {
        this.mAnimateMarkersOnStillHighlight = animateMarkerOnHighlight;
        if (mMarkerList != null) {
            for (final Marker marker : mMarkerList) {
                marker.setShouldAnimateWhenHighlighted(animateMarkerOnHighlight);
            }
        }
        postInvalidate();
    }

    /**
     * Get the center circle object.
     *
     * @return The center circle object.
     */
    public CircularViewObject getCenterCircle() {
        return mCircle;
    }

    /**
     * Get the marker that is currently highlighted. Null is returned if no marker is highlighted.
     *
     * @return The marker that is currently highlighted.
     */
    public Marker getHighlightedMarker() {
        return mHighlightedMarker;
    }

    /**
     * Returns the flag the determines if the highlighted marker will draw on top of other markers.
     * See R.styleable#CircularView_drawHighlightedMarkerOnTop
     *
     * @return True if the highlighted marker will draw on top of other markers, false if they're all drawn in order.
     */
    public boolean isDrawHighlightedMarkerOnTop() {
        return mDrawHighlightedMarkerOnTop;
    }

    /**
     * Set the flag that determines if the highlighted marker will draw on top of other markers.
     * This is false by default.
     * See R.styleable#CircularView_drawHighlightedMarkerOnTop
     *
     * @param drawHighlightedMarkerOnTop the flag that determines if the highlighted marker will draw on top of other markers.
     */
    public void setDrawHighlightedMarkerOnTop(boolean drawHighlightedMarkerOnTop) {
        this.mDrawHighlightedMarkerOnTop = drawHighlightedMarkerOnTop;
    }

    private boolean mLongClickRegistered = false;
    private Marker mTouchEventMarker = null;
    private Integer mTouchEventMarkerPos = null;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = false;

        // check all markers
        if (mMarkerList != null) {
            // check to see if the highlighted marker is on top. If so, check it before the other markers.
            boolean highlightedMarkerHandlesEvent = false;
            if (mHighlightedMarker != null && mDrawHighlightedMarkerOnTop) {
                final int status = mHighlightedMarker.onTouchEvent(event);
                if (status >= 0) {
                    handled = status != MotionEvent.ACTION_MOVE;
                    mTouchEventMarker = mHighlightedMarker;
                    mTouchEventMarkerPos = mHighlightedMarkerPosition;
                    if (status == MotionEvent.ACTION_UP && mOnCircularViewObjectClickListener != null) {
                        if (mLongClickRegistered) {
                            mLongClickRegistered = false;
                        } else {
                            playSoundEffect(SoundEffectConstants.CLICK);
                            mOnCircularViewObjectClickListener.onMarkerClick(this, mHighlightedMarker, mHighlightedMarkerPosition, false);
                        }
                    }
                    highlightedMarkerHandlesEvent = true;
                }
            }

            if (!highlightedMarkerHandlesEvent) {
                final int size = mMarkerList.size();
                // Since markers are drawn first to last, the last marker will be on top, so search
                // for a click from last to first.

                for (int i = size - 1; i > -1; i--) {
                    if (mDrawHighlightedMarkerOnTop && i == mHighlightedMarkerPosition) {
                        // If the marker is to be drawn on top then it will have already been checked
                        // by this point. Don't check it again.
                        continue;
                    }
                    final Marker marker = mMarkerList.get(i);
                    final int status = marker.onTouchEvent(event);
                    if (status >= 0) {
                        handled = status != MotionEvent.ACTION_MOVE;
                        mTouchEventMarker = marker;
                        mTouchEventMarkerPos = i;
                        if (status == MotionEvent.ACTION_UP && mOnCircularViewObjectClickListener != null) {
                            if (mLongClickRegistered) {
                                mLongClickRegistered = false;
                            } else {
                                playSoundEffect(SoundEffectConstants.CLICK);
                                mOnCircularViewObjectClickListener.onMarkerClick(this, marker, i, false);
                            }
                        }
                        break;
                    }
                }
            }
        }

        // check center circle
        if (!handled && mCircle != null) {
            final int status = mCircle.onTouchEvent(event);
            if (status >= 0) {
                handled = true;
                mTouchEventMarker = null;
                mTouchEventMarkerPos = -1;
                if (status == MotionEvent.ACTION_UP && mOnCircularViewObjectClickListener != null) {
                    if (mLongClickRegistered) {
                        mLongClickRegistered = false;
                    } else {
                        playSoundEffect(SoundEffectConstants.CLICK);
                        mOnCircularViewObjectClickListener.onClick(this, false);
                    }
                }
            }
        }

        return super.onTouchEvent(event);
    }

    private OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnCircularViewObjectClickListener != null && isLongClickable()) {
                mLongClickRegistered = true;
                if (mTouchEventMarkerPos == -1) {
                    playSoundEffect(SoundEffectConstants.CLICK);
                    mOnCircularViewObjectClickListener.onClick(CircularView.this, true);
                    mTouchEventMarkerPos = null;
                } else if (mTouchEventMarker != null) {
                    playSoundEffect(SoundEffectConstants.CLICK);
                    mOnCircularViewObjectClickListener.onMarkerClick(CircularView.this, mTouchEventMarker, mTouchEventMarkerPos, true);
                    mTouchEventMarker = null;
                    mTouchEventMarkerPos = null;
                }
                return mLongClickRegistered;
            }
            return false;
        }
    };

    /**
     * Set the click listener that will receive a callback when the center circle is clicked.
     *
     * @param l Listener to receive a callback.
     */
    public void setOnCircularViewObjectClickListener(final OnClickListener l) {
        mOnCircularViewObjectClickListener = l;
    }

    /**
     * Set the listener that will receive callbacks when a highlight animation has ended.
     *
     * @param l Listener to receive callbacks.
     * @see #animateHighlightedDegree(float, float, long)
     */
    public void setOnHighlightAnimationEndListener(final OnHighlightAnimationEndListener l) {
        mOnHighlightAnimationEndListener = l;
    }

    /**
     * Start animating the highlighted degree. This will cancel any current animations of this type.
     * Pass <code>true</code> to {@link #setAnimateMarkerOnStillHighlight(boolean)} in order to see individual
     * marker animations when the highlighted degree reaches each marker.
     *
     * @param startDegree Degree to start the animation at.
     * @param endDegree   Degree to end the animation at.
     * @param duration    Duration the animation should be.
     */
    public void animateHighlightedDegree(final float startDegree, final float endDegree, final long duration) {
        animateHighlightedDegree(startDegree, endDegree, duration, true);
    }

    /**
     * Start animating the highlighted degree. This will cancel any current animations of this type.
     * Pass <code>true</code> to {@link #setAnimateMarkerOnStillHighlight(boolean)} in order to see individual
     * marker animations when the highlighted degree reaches each marker.
     *
     * @param startDegree    Degree to start the animation at.
     * @param endDegree      Degree to end the animation at.
     * @param duration       Duration the animation should be.
     * @param animateMarkers True to animate markers during the animation. False to not animate the markers.
     */
    public void animateHighlightedDegree(final float startDegree, final float endDegree, final long duration, final boolean animateMarkers) {
        mHighlightedDegreeObjectAnimator.cancel();
        mHighlightedDegreeObjectAnimator.setFloatValues(startDegree, endDegree);
        mHighlightedDegreeObjectAnimator.setDuration(duration);
        mAnimateMarkersOnHighlightAnimation = animateMarkers;
        mIsAnimating = true;
        mHighlightedDegreeObjectAnimator.start();
    }

    private boolean mAnimationWasCanceled = false;
    private final Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mAnimateMarkersOnHighlightAnimation = mIsAnimating = false;
            if (!mAnimationWasCanceled) {
                setHighlightedDegree(getHighlightedDegree());
                if (mOnHighlightAnimationEndListener != null && mHighlightedMarker != null) {
                    // Highlighted marker will be set by setHighlightedDegree
                    mOnHighlightAnimationEndListener.onHighlightAnimationEnd(CircularView.this, mHighlightedMarker, mHighlightedMarkerPosition);
                }
            } else {
                mAnimationWasCanceled = false;
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mAnimationWasCanceled = true;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    };

    /**
     * Get the starting point for the markers.
     * See R.styleable#CircularView_markerStartingPoint
     *
     * @return The starting point for the markers.
     */
    public float getMarkerStartingPoint() {
        return mMarkerStartingPoint;
    }

    /**
     * Set the starting point for the markers
     * See R.styleable#CircularView_markerStartingPoint
     *
     * @param startingPoint Starting point for the markers
     */
    public void setMarkerStartingPoint(final float startingPoint) {
        mMarkerStartingPoint = startingPoint;
        requestLayout();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Remove all callback references from the center circle
        mCircle.setCallback(null);
        // Remove all callback references from the markers
        if (mMarkerList != null) {
            for (final Marker marker : mMarkerList) {
                marker.cancelAnimation();
                marker.setCallback(null);
            }
        }
        // Unregister adapter observer
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mAdapterDataSetObserver);
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            requestLayout();
        }

        /**
         * Does the same thing as {@link #onChanged()}.
         */
        @Override
        public void onInvalidated() {
            postInvalidate();
        }
    }

    /**
     * Use this to register for click event callbacks from CircularEventObjects
     */
    public interface OnClickListener {
        /**
         * Called when the center view object has been clicked.
         *
         * @param view        The circular view that was clicked.
         * @param isLongClick True if this click is coming from a long click, false if not.
         */
        public void onClick(CircularView view, boolean isLongClick);

        /**
         * Called when a marker is clicked.
         *
         * @param view        The circular view that the marker belongs to
         * @param marker      The marker that was clicked.
         * @param position    The position of the marker in the adapter
         * @param isLongClick True if this click is coming from a long click, false if not.
         */
        public void onMarkerClick(CircularView view, Marker marker, int position, boolean isLongClick);
    }

    /**
     * Use this to register for callback events when the highlight animation finishes executing.
     */
    public interface OnHighlightAnimationEndListener {
        /**
         * Called when the highlight animation ends. This is <b>not</b> called when the animation is canceled.
         *
         * @param view     The circular view that the marker belongs to.
         * @param marker   The circular view object that the animation ended on.
         * @param position The position of the marker in the adapter.
         */
        public void onHighlightAnimationEnd(CircularView view, Marker marker, int position);
    }
}