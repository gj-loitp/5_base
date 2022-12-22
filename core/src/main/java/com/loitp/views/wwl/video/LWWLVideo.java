package com.loitp.views.wwl.video;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import com.loitpcore.R;
import com.loitp.core.utilities.LAppResource;
import com.loitp.views.wwl.music.utils.LWWLMusicIllegal;
import com.loitp.views.wwl.music.utils.LWWLMusicViewHelper;

import java.util.LinkedList;

import kotlin.Suppress;

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
//14.12.2020 try to convert to kotlin but failed
public class LWWLVideo extends ViewGroup {
    public static final int STATE_HIDED = 0;
    public static final int STATE_MAXIMIZED = 1;
    public static final int STATE_MINIMIZED = 2;
    public final Rect mDefaultMiniPlayerRect;
    private final Rect mMiniPlayerRect;
    private final Rect mFullPlayerRect;
    private final Rect mPlayerRect;
    private final Scroller mVScroller;
    private final Scroller mHScroller;
    private final Tracker mTracker;
    private final DecelerateInterpolator mDecelerateInterpolator;
    private final Drawable mInnerglowDrawable;
    private final Drawable mShadowDrawable;
    private final int mPlayerShadowSize;
    private final int mPlayerViewId;
    private final int mMetadataViewId;
    private final int mMetadataPanelViewId;
    private final int mOverlayViewId;
    private final int mMiniPlayerWidth;
    private final int mMiniPlayerPadding;
    private final int mDismissDragDistance;
    private final int mDismissAnimationDistance;
    private final int mLayoutMode;
    public int mOffsetX;
    public int mMaxY;
    public int mState;
    public boolean mIsFullscreen;
    private Listener mListener;
    private float mCurrentAlpha;
    private int mDragState;
    private final Rect mPlayerOuterRect;
    private Rect mMixRect;
    private boolean mIsHardware;
    private boolean mIsReady;
    private View mPlayerView;
    private View mMetadataView;
    private View mMetadataPanelView;
    private View mOverlayView;
    private View mBgView;
    private LinkedList mViews;
    private int mCurrentY;
    private boolean mIsHScroll;
    private int mOffsetH;

    public LWWLVideo(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = context.getResources();
        this.mMiniPlayerRect = new Rect();
        this.mDefaultMiniPlayerRect = new Rect();
        this.mFullPlayerRect = new Rect();
        this.mPlayerRect = new Rect();
        this.mIsReady = false;
        this.mState = STATE_HIDED;
        this.mVScroller = new Scroller(context, new DecelerateInterpolator());
        this.mHScroller = new Scroller(context, new DecelerateInterpolator());
        this.mTracker = new Tracker(this, context);
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.mDragState = DragOrientation.DRAGGING_NONE;
        this.mDismissDragDistance = (int) resources.getDimension(R.dimen.watch_while_mini_player_dismiss_drag_distance);
        this.mDismissAnimationDistance = (int) resources.getDimension(R.dimen.watch_while_mini_player_dismiss_animation_distance);
        this.mInnerglowDrawable = DrawableHelper.get(context, R.drawable.miniplayer_innerglow);
        this.mShadowDrawable = DrawableHelper.get(context, R.drawable.miniplayer_shadow);
        this.mPlayerShadowSize = (int) resources.getDimension(R.dimen.watch_while_mini_player_shadow_size);
        this.mPlayerOuterRect = new Rect();

        @SuppressLint("CustomViewStyleable")
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.WatchWhileLayout);
        this.mPlayerViewId = typedArray.getResourceId(R.styleable.WatchWhileLayout_playerViewId, 0);
        this.mMetadataViewId = typedArray.getResourceId(R.styleable.WatchWhileLayout_metadataViewId, 0);
        this.mMetadataPanelViewId = typedArray.getResourceId(R.styleable.WatchWhileLayout_metadataPanelViewId, 0);
        this.mOverlayViewId = typedArray.getResourceId(R.styleable.WatchWhileLayout_overlayViewId, 0);
        this.mLayoutMode = typedArray.getBoolean(R.styleable.WatchWhileLayout_tabletLayout, false) ? Orientation.LANDSCAPE : Orientation.PORTRAIT;
        this.mMiniPlayerWidth = (int) typedArray.getDimension(R.styleable.WatchWhileLayout_miniPlayerWidth, 240.0f);
        this.mMiniPlayerPadding = (int) typedArray.getDimension(R.styleable.WatchWhileLayout_miniPlayerPadding, 12.0f);
        typedArray.recycle();
        LWWLMusicIllegal.INSTANCE.check(this.mPlayerViewId != 0, "playerViewId must be specified");
        LWWLMusicIllegal.INSTANCE.check(this.mMetadataViewId != 0, "metadataViewId must be specified");
        if (isTablet()) {
            LWWLMusicIllegal.INSTANCE.check(this.mMetadataPanelViewId != 0, "metadataLandscapeTitleViewId must be specified");
        }
    }

    private static void updateRect(Rect rect, int left, int top, int right, int bottom) {
        rect.set(left, top, left + right, top + bottom);
    }

    private static int distanceVDuration(float alpha, int from, int to) {
        return Math.round(Math.min(Math.max(alpha, 0.0f), 1.0f) * ((float) (to - from))) + from;
    }

    private static void setViewAlpha(View view, float alpha) {
        if (view != null) {
            view.setAlpha(alpha);
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        boolean isTL = isTablet();
        int requireViewCount = isTL ? 4 : 3;
        LWWLMusicIllegal.INSTANCE.check(childCount >= requireViewCount, "WatchWhileLayout must have at least " + requireViewCount + " children");
        this.mViews = new LinkedList();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            int id = view.getId();
            if (this.mPlayerView == null && this.mPlayerViewId == id) {
                this.mPlayerView = view;
            } else if (this.mMetadataView == null && this.mMetadataViewId == id) {
                this.mMetadataView = view;
            } else if (this.mMetadataPanelView == null && this.mMetadataPanelViewId == id) {
                this.mMetadataPanelView = view;
            } else if (this.mOverlayView == null && this.mOverlayViewId == id) {
                this.mOverlayView = view;
            } else {
                this.mViews.add(view);
            }
        }
        LWWLMusicIllegal.INSTANCE.check(this.mPlayerView);
        LWWLMusicIllegal.INSTANCE.check(this.mMetadataView);
        if (isTL) {
            LWWLMusicIllegal.INSTANCE.check(this.mMetadataPanelView);
        }
        LWWLMusicIllegal.INSTANCE.check(this.mViews.size() > 0, "contentViews cannot be empty");
        this.mPlayerView.setOnClickListener(new PlayerViewClickListener(this));
        this.mBgView = new View(getContext());
//        this.mBgView.setBackgroundColor(getResources().getColor(android.R.color.black));
        this.mBgView.setBackgroundColor(LAppResource.INSTANCE.getColor(R.color.black));
        addView(this.mBgView);
        bringChildToFront(this.mBgView);
        if (this.mMetadataPanelView != null) {
            bringChildToFront(this.mMetadataPanelView);
        }
        bringChildToFront(this.mMetadataView);
        bringChildToFront(this.mPlayerView);
        bringChildToFront(this.mOverlayView);
        updateVisibility();
    }

    @Override
    public void computeScroll() {
        if (!this.mIsFullscreen) {
            if (this.mVScroller.computeScrollOffset()) {
                setAnimatePos(this.mVScroller.getCurrY());
                if (this.mVScroller.isFinished()) {
                    if (this.mCurrentY <= 0) {
                        slideTo(STATE_MAXIMIZED);
                    } else if (this.mCurrentY >= this.mMaxY) {
                        slideTo(STATE_MINIMIZED);
                    }
                }
                invalidate();
            } else if (this.mHScroller.computeScrollOffset()) {
                slide(this.mHScroller.getCurrX());
                if (this.mHScroller.isFinished()) {
                    if (this.mIsHScroll) {
                        slideTo(STATE_HIDED);
                    } else {
                        slideTo(STATE_MINIMIZED);
                    }
                }
                invalidate();
            } else if (!isDragging()) {
                if (this.mCurrentY != 0 && this.mCurrentY != this.mMaxY) {
                    autoState();
                } else if (this.mOffsetX != 0) {
                    slideTo(getState());
                }
            }
        }
    }

    public boolean canAnimate() {
        return !(this.mIsFullscreen || this.mState == STATE_HIDED);
    }

    public boolean setAnimatePos(int pos) {
        if (!canAnimate() || this.mCurrentY == pos) {
            return false;
        }
        this.mCurrentY = pos;
        this.mOffsetX = 0;
        resetHScroller();
        layout();
        if (this.mListener != null) {
            this.mListener.WWL_onSliding(this.mCurrentAlpha);
        }
        return true;
    }

    public void updateSizeChanged(int width, int height) {
        int fullPlayerInnerHeight;
        int miniPlayerInnerHeight;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int innerWidth = (width - paddingLeft) - getPaddingRight();
        int innerHeight = (height - paddingTop) - getPaddingBottom();
        float playerRatio = getPlayerRatio();
        miniPlayerInnerHeight = (int) (((float) this.mMiniPlayerWidth) / playerRatio);
        if (!isLandscape()) {
            fullPlayerInnerHeight = (int) (((float) innerWidth) / playerRatio);
            updateRect(this.mFullPlayerRect, paddingLeft, paddingTop, innerWidth, fullPlayerInnerHeight);
        } else if (isTablet()) {
            Context context = getContext();
            LWWLMusicIllegal.INSTANCE.check(context);
            int fullPlayerInnerWidth;
            if (context.getResources().getConfiguration().smallestScreenWidthDp >= 720) {
                fullPlayerInnerWidth = Math.round(0.7f * ((float) innerWidth));
            } else {
                fullPlayerInnerWidth = Math.round(0.65f * ((float) innerWidth));
            }
            fullPlayerInnerHeight = (int) (((float) fullPlayerInnerWidth) / playerRatio);
            updateRect(this.mFullPlayerRect, paddingLeft, paddingTop, fullPlayerInnerWidth, fullPlayerInnerHeight);
        } else {
            updateRect(this.mFullPlayerRect, paddingLeft, paddingTop, innerWidth, innerHeight);
            fullPlayerInnerHeight = innerHeight;
        }
        if (this.mDefaultMiniPlayerRect.isEmpty()) {
            updateRect(this.mMiniPlayerRect, (innerWidth - this.mMiniPlayerPadding) - this.mMiniPlayerWidth, ((height - getPaddingBottom()) - this.mMiniPlayerPadding) - miniPlayerInnerHeight, this.mMiniPlayerWidth, miniPlayerInnerHeight);
        } else {
            this.mMiniPlayerRect.set(this.mDefaultMiniPlayerRect);
        }
        int cenX = ((this.mMiniPlayerRect.left + this.mMiniPlayerRect.right) / 2) - ((this.mFullPlayerRect.left + this.mFullPlayerRect.right) / 2);
        int cenY = ((this.mMiniPlayerRect.top + this.mMiniPlayerRect.bottom) / 2) - ((this.mFullPlayerRect.top + this.mFullPlayerRect.bottom) / 2);
        if (Math.abs(cenY) > Math.abs(cenX * 2)) {
            this.mTracker.fraction = 0.0f;
            this.mMaxY = (innerHeight - this.mMiniPlayerPadding) - ((miniPlayerInnerHeight + fullPlayerInnerHeight) / 2);
        } else {
            this.mTracker.fraction = (float) Math.atan2(cenY, cenX);
            this.mMaxY = (int) Math.sqrt((cenX * cenX) + (cenY * cenY));
        }
        if (!this.mVScroller.isFinished()) {
            this.mCurrentY = (int) (this.mCurrentAlpha * ((float) this.mMaxY));
            if (this.mVScroller.getFinalY() <= 0) {
                maximize(false);
            } else {
                minimize(false);
            }
        } else if (!this.mHScroller.isFinished()) {
            slideTo(this.mIsHScroll ? STATE_HIDED : STATE_MINIMIZED);
        } else if (this.mState != STATE_HIDED) {
            this.mCurrentY = this.mState == STATE_MINIMIZED ? this.mMaxY : 0;
        }
        this.mMixRect = null;
        updateLayerType(false);
        layout();
        measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    public void enterFullscreen() {
        this.mIsFullscreen = true;
        slideTo(STATE_MAXIMIZED);
        reset();
        updateLayerType(false);
        requestLayout();
    }

    public void exitFullscreen() {
        this.mIsFullscreen = false;
        slideTo(STATE_MAXIMIZED);
    }

    public void exitFullscreenToMinimize() {
        this.mIsFullscreen = false;
        slideTo(STATE_MINIMIZED);
    }

    public void slideTo(int state) {
        if (this.mState != state) {
            this.mState = state;
            if (this.mState == STATE_HIDED && this.mIsFullscreen) {
                this.mIsFullscreen = false;
            }
            if (canAnimate()) {
                setAnimatePos(this.mState == STATE_MAXIMIZED ? 0 : this.mMaxY);
            }
            reset();
        }
        this.mOffsetX = 0;
        if (this.mListener != null) {
            switch (this.mState) {
                case STATE_HIDED:
                    this.mListener.WWL_onHided();
                    break;
                case STATE_MAXIMIZED:
                    this.mListener.WWL_maximized();
                    break;
                case STATE_MINIMIZED:
                    this.mListener.WWL_minimized();
                    break;
            }
        }
        updateLayerType(false);
        layout();
        if (!this.mDefaultMiniPlayerRect.isEmpty()) {
            this.mDefaultMiniPlayerRect.setEmpty();
            updateSizeChanged(getWidth(), getHeight());
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!canAnimate() || !isEnabled()) {
            return false;
        }
        switch (motionEvent.getActionMasked()) {
            case ACTION_DOWN:
                if (this.mPlayerRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    this.mTracker.storeE(motionEvent);
                    return !(this.mVScroller.isFinished() || this.mHScroller.isFinished());
                }
                break;
            case ACTION_UP:
            case ACTION_CANCEL:
                stopDrag();
                this.mTracker.pointerId = -1;
                break;
            case ACTION_MOVE:
                if (!isDragging()) {
                    int dragState = DragOrientation.DRAGGING_NONE;
                    int findPointerIndex = motionEvent.findPointerIndex(this.mTracker.pointerId);
                    if (findPointerIndex < 0 || motionEvent.getPointerCount() <= findPointerIndex) {
                        this.mTracker.pointerId = -1;
                        dragState = DragOrientation.DRAGGING_NONE;
                    } else {
                        float tEX = this.mTracker.eX;
                        float tEY = this.mTracker.eY;
                        int mx = this.mTracker.getMX(motionEvent);
                        int my = this.mTracker.getMY(motionEvent);
                        boolean minimized = this.mState == STATE_MINIMIZED && this.mCurrentY == this.mMaxY;
                        int direction = this.mTracker.calc(mx, my);
                        if (minimized) {
                            if (Math.abs(mx) > this.mTracker.scaledPagingTouchSlop * 2 && (this.mTracker.fraction == 0.0f || Math.abs(my) < this.mTracker.scaledPagingTouchSlop)) {
                                dragState = DragOrientation.DRAGGING_HORIZONTAL;
                            } else if (direction > this.mTracker.scaledPagingTouchSlop * 2) {
                                dragState = DragOrientation.DRAGGING_VERTICAL;
                            }
                        } else if (Math.abs(direction) > this.mTracker.scaledPagingTouchSlop) {
                            dragState = DragOrientation.DRAGGING_VERTICAL;
                        }
                        this.mTracker.eX = tEX;
                        this.mTracker.eY = tEY;
                    }
                    if (!(dragState == DragOrientation.DRAGGING_NONE || this.mDragState == dragState)) {
                        updateLayerType(true);
                        this.mDragState = dragState;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    }
                    break;
                }
                return true;
            case ACTION_POINTER_UP:
                this.mTracker.storeEPU(motionEvent);
                break;
        }
        return isDragging();
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean smooth = false;
        this.mTracker.track(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case ACTION_DOWN:
                reset();
                this.mTracker.storeE(motionEvent);
                break;
            case ACTION_UP:
                if (this.mDragState == DragOrientation.DRAGGING_HORIZONTAL) {
                    int direction = this.mTracker.calcDirection(motionEvent, DragOrientation.DRAGGING_HORIZONTAL);
                    boolean isPositive = direction == Sign.SIGN_POSITIVE;
                    boolean isNegative = direction == Sign.SIGN_NEGATIVE;
                    boolean isZero = direction == Sign.SIGN_ZERO;
                    if (this.mOffsetX < (-this.mDismissDragDistance)) {
                        if (isNegative) {
                            slideHorizontalRollBack(true);
                        } else {
                            if (!isZero) {
                                smooth = true;
                            }
                            slideHorizontal(smooth);
                        }
                    } else if (this.mOffsetX > this.mDismissDragDistance) {
                        if (isPositive) {
                            slideHorizontalRollBack(true);
                        } else {
                            if (!isZero) {
                                smooth = true;
                            }
                            slideHorizontal(smooth);
                        }
                    } else if (this.mOffsetX < -20 && isPositive) {
                        slideHorizontal(true);
                    } else if (this.mOffsetX > 20 && isNegative) {
                        slideHorizontal(true);
                    } else slideHorizontalRollBack(!isZero);
                } else if (this.mDragState == DragOrientation.DRAGGING_VERTICAL) {
                    int direction = this.mTracker.calcDirection(motionEvent, DragOrientation.DRAGGING_VERTICAL);
                    if (direction == Sign.SIGN_NEGATIVE && this.mState == STATE_MAXIMIZED) {
                        minimize(true);
                    } else if (direction == Sign.SIGN_POSITIVE && this.mState == STATE_MINIMIZED && this.mCurrentY < this.mMaxY) {
                        maximize(true);
                    } else {
                        autoState();
                    }
                }
                stopDrag();
                this.mTracker.pointerId = -1;
                break;
            case ACTION_MOVE:
                if (isDragging()) {
                    if (this.mDragState != DragOrientation.DRAGGING_HORIZONTAL) {
                        Tracker tracker = this.mTracker;
                        setAnimatePos(Math.min(Math.max((-tracker.calc(tracker.getMX(motionEvent), tracker.getMY(motionEvent))) + this.mCurrentY, 0), this.mMaxY));
                        break;
                    }
                    if (this.mState == STATE_MINIMIZED) {
                        slide(-this.mTracker.getMX(motionEvent) + this.mOffsetX);
                        break;
                    }
                }
                break;
            case ACTION_CANCEL:
                stopDrag();
                this.mTracker.pointerId = -1;
                break;
            case ACTION_POINTER_UP:
                this.mTracker.storeEPU(motionEvent);
                break;
        }
        return true;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        this.mTracker.pointerId = -1;
    }

    public void reset() {
        if (!this.mVScroller.isFinished()) {
            this.mVScroller.abortAnimation();
        }
        resetHScroller();
    }

    public void maximize(boolean smooth) {
        if (!this.mIsFullscreen && this.mState != STATE_HIDED) {
            int dy = -this.mCurrentY;
            if (dy == 0) {
                slideTo(STATE_MAXIMIZED);
                return;
            }

            int duration = calcDuration(dy, this.mMaxY, getDurationMilis(), smooth);
            reset();
            updateLayerType(true);
            this.mVScroller.startScroll(0, this.mCurrentY, 0, dy, duration);
            invalidate();
        }
    }

    public void minimize(boolean smooth) {
        if (!this.mIsFullscreen && this.mState != STATE_HIDED) {
            int dy = this.mMaxY - this.mCurrentY;
            if (dy == 0) {
                slideTo(STATE_MINIMIZED);
                return;
            }
            int duration = calcDuration(dy, this.mMaxY, getDurationMilis(), smooth);
            reset();
            updateLayerType(true);
            this.mVScroller.startScroll(0, this.mCurrentY, 0, dy, duration);
            invalidate();
        }
    }

    private void slideHorizontalRollBack(boolean smooth) {
        if (!this.mIsFullscreen && this.mState == STATE_MINIMIZED) {
            int abs = Math.abs(this.mOffsetX);
            if (abs == 0) {
                slideTo(STATE_MINIMIZED);
                return;
            }
            int duration = calcDuration(abs, Math.max(this.mDismissDragDistance, abs), 250, smooth);
            reset();
            this.mHScroller.startScroll(this.mOffsetX, 0, -this.mOffsetX, 0, duration);
            invalidate();
        }
    }

    public void slideHorizontal(boolean smooth) {
        if (!this.mIsFullscreen && this.mState == STATE_MINIMIZED) {
            int duration = 250;
            if (smooth) {
                duration = 187;
            }
            reset();
            this.mIsHScroll = true;
            this.mOffsetH = this.mOffsetX;
            this.mHScroller.startScroll(this.mOffsetX, 0, this.mOffsetX < 0 ? -this.mDismissAnimationDistance : this.mDismissAnimationDistance, 0, duration);
            invalidate();
        }
    }

    public Parcelable onSaveInstanceState() {
        MParcelable parcelable = new MParcelable(super.onSaveInstanceState());
        parcelable.state = canAnimate() ? getState() : this.mState;
        return parcelable;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof MParcelable) {
            MParcelable p = (MParcelable) parcelable;
            super.onRestoreInstanceState(p.getSuperState());
            slideTo(p.state);
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateSizeChanged(w, h);
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateSizeChanged(getWidth(), getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("WatchWhileLayout can only be used in EXACTLY parrallaxMode.");
        } else if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("WatchWhileLayout can only be used in EXACTLY parrallaxMode.");
        } else {
            int sizeW = MeasureSpec.getSize(widthMeasureSpec);
            int sizeH = MeasureSpec.getSize(heightMeasureSpec);
            if (this.mIsFullscreen) {
                this.mPlayerView.measure(MeasureSpec.makeMeasureSpec(sizeW, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(sizeH, MeasureSpec.EXACTLY));
                return;
            }
            sizeW -= getPaddingLeft() + getPaddingRight();
            sizeH -= getPaddingTop() + getPaddingBottom();
            int makeMeasureSpecW = MeasureSpec.makeMeasureSpec(sizeW, MeasureSpec.EXACTLY);
            int makeMeasureSpecH = MeasureSpec.makeMeasureSpec(sizeH, MeasureSpec.EXACTLY);
            this.mPlayerView.measure(MeasureSpec.makeMeasureSpec(this.mPlayerRect.width(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(this.mPlayerRect.height(), MeasureSpec.EXACTLY));
            if (!this.mIsHardware) {
                if (!isLandscape()) {
                    this.mMetadataView.measure(makeMeasureSpecW, MeasureSpec.makeMeasureSpec(sizeH - this.mFullPlayerRect.height(), MeasureSpec.EXACTLY));
                } else if (isTablet()) {
                    this.mMetadataView.measure(MeasureSpec.makeMeasureSpec(sizeW - this.mFullPlayerRect.width(), MeasureSpec.EXACTLY), makeMeasureSpecH);
                    this.mMetadataPanelView.measure(MeasureSpec.makeMeasureSpec(this.mFullPlayerRect.width(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(Math.max(0, sizeH - this.mFullPlayerRect.height()), MeasureSpec.EXACTLY));
                }
                this.mBgView.measure(widthMeasureSpec, heightMeasureSpec);
                if (this.mOverlayView != null) {
                    this.mOverlayView.measure(widthMeasureSpec, heightMeasureSpec);
                }
                for (Object mView : this.mViews) {
                    ((View) mView).measure(makeMeasureSpecW, makeMeasureSpecH);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        updateVisibility();
        if (this.mIsFullscreen) {
            this.mPlayerView.layout(l, t, r, b);
            return;
        }
        this.mPlayerView.layout(this.mPlayerRect.left, this.mPlayerRect.top, this.mPlayerRect.right, this.mPlayerRect.bottom);
        if (this.mMixRect != null) {
            this.mMixRect.set(this.mPlayerOuterRect);
        }
        if (this.mCurrentY <= 0) {
            this.mPlayerOuterRect.set(this.mPlayerRect);
        } else {
            this.mPlayerOuterRect.set(this.mPlayerRect.left - this.mPlayerShadowSize, this.mPlayerRect.top - this.mPlayerShadowSize, this.mPlayerRect.right + this.mPlayerShadowSize, this.mPlayerRect.bottom + this.mPlayerShadowSize);
        }
        if (this.mMixRect != null) {
            this.mMixRect.union(this.mPlayerOuterRect);
        } else {
            this.mMixRect = new Rect(this.mPlayerOuterRect);
        }
        float alpha = 0.0f;
        if (this.mCurrentAlpha < 1.0f) {
            alpha = this.mCurrentAlpha;
        } else if (this.mCurrentAlpha < 2.0f) {
            alpha = 2.0f - this.mCurrentAlpha;
        }
        if (this.mInnerglowDrawable != null) {
            this.mInnerglowDrawable.setBounds(this.mPlayerRect);
            this.mInnerglowDrawable.setAlpha(LWWLMusicViewHelper.alpha255(alpha));
        }
        this.mShadowDrawable.setBounds(this.mPlayerOuterRect);
        this.mShadowDrawable.setAlpha(LWWLMusicViewHelper.alpha255(alpha));
        invalidate(this.mMixRect.left, this.mMixRect.top, this.mMixRect.right, this.mMixRect.bottom);
        int width = getWidth();
        int height = getHeight();
        if (this.mIsHardware) {
            if (isLandscape() && isTablet()) {
                int metaX = distanceVDuration(this.mCurrentAlpha, 0, width - this.mFullPlayerRect.right) + this.mPlayerRect.right;
                int metaPanelX = metaX - this.mFullPlayerRect.width();
                int metaPanelY = distanceVDuration(this.mCurrentAlpha, 0, (height - this.mFullPlayerRect.bottom) + (this.mMetadataPanelView.getMeasuredHeight() * 2)) + this.mPlayerRect.bottom;
                this.mMetadataView.setTranslationX((float) (metaX - this.mMetadataView.getLeft()));
                this.mMetadataPanelView.setTranslationX((float) (metaPanelX - this.mMetadataPanelView.getLeft()));
                this.mMetadataPanelView.setTranslationY((float) (metaPanelY - this.mMetadataPanelView.getTop()));
                return;
            }
            int Y;
            if (isLandscape()) {
                Y = height - getPaddingBottom();
            } else {
                Y = this.mPlayerRect.bottom + distanceVDuration(this.mCurrentAlpha, 0, this.mMiniPlayerPadding + this.mMiniPlayerRect.height());
                this.mMetadataView.setTranslationY((float) (Y - this.mMetadataView.getTop()));
            }
            this.mBgView.setTranslationY((float) Math.min(Y - this.mBgView.getMeasuredHeight(), 0));
            return;
        }
        this.mMetadataView.setTranslationX(0.0f);
        this.mMetadataView.setTranslationY(0.0f);
        if (this.mMetadataPanelView != null) {
            this.mMetadataPanelView.setTranslationX(0.0f);
            this.mMetadataPanelView.setTranslationY(0.0f);
        }
        this.mBgView.setTranslationX(0.0f);
        this.mBgView.setTranslationY(0.0f);
        width = r - l;
        height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (isLandscape() && isTablet()) {
            int metaLeft = this.mPlayerRect.right + distanceVDuration(this.mCurrentAlpha, 0, width - this.mFullPlayerRect.right);
            int metaPanelLeft = metaLeft - this.mFullPlayerRect.width();
            int metaPanelTop = this.mPlayerRect.bottom + distanceVDuration(this.mCurrentAlpha, 0, (height - this.mFullPlayerRect.bottom) + (this.mMetadataPanelView.getMeasuredHeight() * 2));
            this.mMetadataView.layout(metaLeft, paddingTop, this.mMetadataView.getMeasuredWidth() + metaLeft, this.mMetadataView.getMeasuredHeight() + paddingTop);
            this.mMetadataPanelView.layout(metaPanelLeft, metaPanelTop, this.mMetadataPanelView.getMeasuredWidth() + metaPanelLeft, this.mMetadataPanelView.getMeasuredHeight() + metaPanelTop);
        } else if (isLandscape()) {
            getPaddingBottom();
        } else {
            int top = this.mPlayerRect.bottom + distanceVDuration(this.mCurrentAlpha, 0, this.mMiniPlayerPadding + this.mMiniPlayerRect.height());
            this.mMetadataView.layout(paddingLeft, top, this.mMetadataView.getMeasuredWidth() + paddingLeft, this.mMetadataView.getMeasuredHeight() + top);
        }
        this.mBgView.layout(0, 0, width, height);
        if (this.mOverlayView != null) {
            this.mOverlayView.layout(paddingLeft, paddingTop, this.mOverlayView.getMeasuredWidth() + paddingLeft, this.mOverlayView.getMeasuredHeight() + paddingTop);
        }
        for (Object mView : this.mViews) {
            View view = (View) mView;
            view.layout(paddingLeft, paddingTop, view.getMeasuredWidth() + paddingLeft, view.getMeasuredHeight() + paddingTop);
        }
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect rect) {
        if (this.mIsFullscreen || this.mState == STATE_MAXIMIZED) {
            return this.mPlayerView.requestFocus(direction, rect);
        }
        return ((View) this.mViews.get(0)).requestFocus(direction, rect);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View view, long drawingTime) {
        if (view != this.mPlayerView) {
            return super.drawChild(canvas, view, drawingTime);
        }
        boolean drawChild = super.drawChild(canvas, view, drawingTime);
        if (this.mIsFullscreen || this.mState == STATE_HIDED || this.mCurrentY <= 0) {
            return drawChild;
        }
        this.mShadowDrawable.draw(canvas);
        if (this.mInnerglowDrawable == null) {
            return drawChild;
        }
        this.mInnerglowDrawable.draw(canvas);
        return drawChild;
    }

    private float getPlayerRatio() {
        return 1.777f;
    }

    private boolean slide(int offset) {
        if (!canAnimate() || this.mOffsetX == offset) {
            return false;
        }
        this.mOffsetX = offset;
        layout();
        if (this.mListener != null) {
            this.mListener.WWL_onSliding(this.mCurrentAlpha);
        }
        return true;
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private boolean isTablet() {
        return this.mLayoutMode == Orientation.LANDSCAPE;
    }

    private void layout() {
        if (this.mState != STATE_HIDED) {
            if (this.mCurrentY <= 0) {
                this.mCurrentAlpha = 0.0f;
                this.mPlayerRect.set(this.mFullPlayerRect);
            } else if (this.mCurrentY < this.mMaxY) {
                this.mCurrentAlpha = ((float) this.mCurrentY) / ((float) this.mMaxY);
                this.mPlayerRect.set(
                        distanceVDuration(this.mCurrentAlpha, this.mFullPlayerRect.left, this.mMiniPlayerRect.left),
                        distanceVDuration(this.mCurrentAlpha, this.mFullPlayerRect.top, this.mMiniPlayerRect.top),
                        distanceVDuration(this.mCurrentAlpha, this.mFullPlayerRect.right, this.mMiniPlayerRect.right),
                        distanceVDuration(this.mCurrentAlpha, this.mFullPlayerRect.bottom, this.mMiniPlayerRect.bottom)
                );
            } else {
                int i;
                this.mCurrentAlpha = 1.0f;
                this.mPlayerRect.set(this.mMiniPlayerRect);
                if (this.mOffsetX != 0) {
                    i = Math.abs(this.mOffsetX);
                    if (this.mIsHScroll) {
                        i -= Math.abs(this.mOffsetH);
                        if (i >= this.mDismissAnimationDistance) {
                            this.mCurrentAlpha = 3.0f;
                        } else {
                            this.mCurrentAlpha = (((float) i) / ((float) this.mDismissAnimationDistance)) + 2.0f;
                        }
                    } else if (i >= this.mDismissDragDistance) {
                        this.mCurrentAlpha = 2.0f;
                    } else {
                        this.mCurrentAlpha = (((float) i) / ((float) this.mDismissDragDistance)) + 1.0f;
                    }
                }
                int mStart = 0;
                i = mStart + this.mMiniPlayerPadding;
                if (i <= this.mMiniPlayerRect.left) {
                    i = 0;
                } else {
                    i -= this.mMiniPlayerRect.left;
                }
                i += this.mOffsetX;
                this.mPlayerRect.left = this.mMiniPlayerRect.left + i;
                this.mPlayerRect.right = i + this.mMiniPlayerRect.right;
            }
            requestLayout();
            invalidate();
        }
    }

    private void updateVisibility() {
        int v_views = GONE;
        if (this.mIsFullscreen) {
            this.mPlayerView.setVisibility(VISIBLE);
            this.mMetadataView.setVisibility(GONE);
            if (this.mMetadataPanelView != null) {
                this.mMetadataPanelView.setVisibility(GONE);
            }
            this.mBgView.setVisibility(GONE);
        } else {
            int v_playerView = VISIBLE;
            int v_metadataPanelView;
            int v_metadataView;
            int v_bgView;
            if (this.mState != STATE_HIDED) {
                boolean isTL = isTablet();
                boolean isLandscape = isLandscape();
                boolean isNLM = !isTL && isLandscape;
                boolean isLM = isTL && isLandscape;
                if (this.mCurrentY < this.mMaxY) {
                    if (isNLM) {
                        v_metadataPanelView = GONE;
                        v_metadataView = GONE;
                    } else {
                        if (isLM) {
                            v_metadataPanelView = VISIBLE;
                        } else {
                            v_metadataPanelView = GONE;
                        }
                        float alpha = 1.0f;
                        if (this.mCurrentAlpha > 0.1f) {
                            alpha = 1.1f - this.mCurrentAlpha;
                        }
                        setViewAlpha(this.mMetadataView, alpha);
                        setViewAlpha(this.mMetadataPanelView, alpha);
                        v_metadataView = VISIBLE;
                    }
                    if (this.mCurrentY > 0) {
                        setViewAlpha(this.mBgView, this.mDecelerateInterpolator.getInterpolation(1.0f - this.mCurrentAlpha) * 0.9f);
                        v_bgView = VISIBLE;
                    } else {
                        v_bgView = GONE;
                    }
                    this.mIsReady = true;
                } else {
                    v_bgView = GONE;
                    v_metadataPanelView = GONE;
                    v_metadataView = GONE;
                }
                if (this.mCurrentY > 0) {
                    v_views = VISIBLE;
                }
            } else {
                invalidate(this.mPlayerOuterRect);
                this.mMixRect = null;
                v_bgView = GONE;
                v_metadataPanelView = GONE;
                v_metadataView = GONE;
                v_playerView = GONE;
                v_views = VISIBLE;
            }
            this.mPlayerView.setVisibility(v_playerView);
            this.mMetadataView.setVisibility(v_metadataView);
            if (this.mMetadataPanelView != null) {
                this.mMetadataPanelView.setVisibility(v_metadataPanelView);
            }
            this.mBgView.setVisibility(v_bgView);
        }
        for (Object mView : this.mViews) {
            ((View) mView).setVisibility(v_views);
        }
    }

    private boolean isDragging() {
        return this.mDragState != DragOrientation.DRAGGING_NONE;
    }

    private void stopDrag() {
        if (isDragging()) {
            this.mDragState = DragOrientation.DRAGGING_NONE;
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private void resetHScroller() {
        if (!this.mHScroller.isFinished()) {
            this.mHScroller.abortAnimation();
        }
        this.mIsHScroll = false;
    }

    private int getDurationMilis() {
        if (isTablet()) {
            return 400;
        }
        return 350;
    }

    private int calcDuration(int from, int to, int duration, boolean smooth) {
        from = Math.abs(from);
        if (from == to) {
            return duration;
        }
        int dur = distanceVDuration(((float) from) / ((float) to), 0, duration);
        if (smooth) {
            dur = (int) (((float) dur) * 0.75f);
        }
        return Math.max(dur, 50);
    }

    private int getState() {
        if (!this.mIsFullscreen) {
            if (this.mIsHScroll) {
                return STATE_HIDED;
            }
            if (this.mOffsetX != 0) {
                if (Math.abs(this.mOffsetX) < this.mDismissDragDistance) {
                    return STATE_MINIMIZED;
                }
                return STATE_HIDED;
            } else if (this.mCurrentY >= this.mMaxY / 2) {
                return STATE_MINIMIZED;
            }
        }
        return STATE_MAXIMIZED;
    }

    private void autoState() {
        switch (getState()) {
            case STATE_HIDED:
                slideTo(STATE_HIDED);
                break;
            case STATE_MAXIMIZED:
                maximize(false);
                break;
            case STATE_MINIMIZED:
                minimize(false);
                break;
            default:
                break;
        }
    }

    private void updateLayerType(boolean hardware) {
        if (this.mIsReady && this.mIsHardware != hardware) {
            this.mIsHardware = hardware;
            int type = LAYER_TYPE_NONE;
            if (hardware) {
                type = LAYER_TYPE_HARDWARE;
            }
            int childCount = getChildCount();
            int i = 0;
            while (i < childCount) {
                View childAt = getChildAt(i);
                if (childAt != this.mPlayerView) {
                    childAt.setLayerType(type, null);
                    childAt.destroyDrawingCache();
                }
                i++;
            }
        }
    }

    public interface Listener {
        void WWL_onSliding(float offset);

        void WWL_onClicked();

        void WWL_onHided();

        void WWL_minimized();

        void WWL_maximized();
    }

    static final class Orientation {
        public static final int LANDSCAPE;
        public static final int PORTRAIT;

        static {
            LANDSCAPE = 1;
            PORTRAIT = 2;
        }
    }

    static final class DragOrientation {
        public static final int DRAGGING_NONE;
        public static final int DRAGGING_VERTICAL;
        public static final int DRAGGING_HORIZONTAL;

        static {
            DRAGGING_NONE = 1;
            DRAGGING_VERTICAL = 2;
            DRAGGING_HORIZONTAL = 3;
        }
    }

    static class DrawableHelper {
        public static Drawable get(Context context, int i) {
            return context.getDrawable(i);
        }
    }

    static final class MParcelable extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR;

        static {
            CREATOR = new Creator() {
                @Override
                public Object createFromParcel(Parcel source) {
                    return new MParcelable(source);
                }

                @Override
                public Object[] newArray(int size) {
                    return new MParcelable[size];
                }
            };
        }

        public int state;

        public MParcelable(Parcel parcel) {
            super(parcel);
            this.state = parcel.readInt();
        }

        public MParcelable(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }
    }

    static final class Sign {
        public static final int SIGN_NEGATIVE;
        public static final int SIGN_POSITIVE;
        public static final int SIGN_ZERO;

        static {
            SIGN_NEGATIVE = 1;
            SIGN_POSITIVE = 2;
            SIGN_ZERO = 3;
        }
    }

    static class Tracker {
        public final int scaledPagingTouchSlop;
        private final int a;
        private final int snapVelocity;
        public LWWLVideo layout;
        public float eX;
        public float eY;
        public int pointerId;
        public float fraction;
        private VelocityTracker velocityTracker;
        private float lEX;
        private float lEY;

        @Suppress(names = "unused")
        public Tracker(Context context) {
            this(context, 200);
        }

        public Tracker(LWWLVideo layout, Context context) {
            this(context, 400);
            this.layout = layout;
            this.fraction = 0.0f;
        }

        public Tracker(Context context, int snapVelocity) {
            this.pointerId = -1;
            LWWLMusicIllegal.INSTANCE.check(snapVelocity >= 200, "snapVelocity cannot be less than 200");
            ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
            this.scaledPagingTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
            this.a = viewConfiguration.getScaledMaximumFlingVelocity();
            this.snapVelocity = snapVelocity;
        }

        public final void track(MotionEvent motionEvent) {
            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }
            this.velocityTracker.addMovement(motionEvent);
        }

        public final void storeE(MotionEvent motionEvent) {
            this.eX = motionEvent.getX();
            this.eY = motionEvent.getY();
            this.lEX = this.eX;
            this.lEY = this.eY;
            this.pointerId = motionEvent.getPointerId(motionEvent.getPointerCount() - 1);
        }

        public final int getMX(MotionEvent motionEvent) {
            int findPointerIndex = motionEvent.findPointerIndex(this.pointerId);
            if (findPointerIndex < 0) {
                return 0;
            }
            float x = motionEvent.getX(findPointerIndex);
            findPointerIndex = (int) (this.eX - x);
            this.eX = x;
            return findPointerIndex;
        }

        public final int getMY(MotionEvent motionEvent) {
            int findPointerIndex = motionEvent.findPointerIndex(this.pointerId);
            if (findPointerIndex < 0) {
                return 0;
            }
            float y = motionEvent.getY(findPointerIndex);
            findPointerIndex = (int) (this.eY - y);
            this.eY = y;
            return findPointerIndex;
        }

        public final int calcDirection(MotionEvent motionEvent, int orientation) {
            int findPointerIndex = motionEvent.findPointerIndex(this.pointerId);

            if (findPointerIndex < 0) {
                return Sign.SIGN_ZERO;
            }
            int delta;
            this.velocityTracker.computeCurrentVelocity(1000, (float) this.a);
            if (orientation == DragOrientation.DRAGGING_VERTICAL) {
                delta = (int) (this.lEY - motionEvent.getY(findPointerIndex));
                findPointerIndex = (int) this.velocityTracker.getYVelocity(this.pointerId);
            } else if (orientation == DragOrientation.DRAGGING_HORIZONTAL) {
                delta = (int) (this.lEX - motionEvent.getX(findPointerIndex));
                findPointerIndex = (int) this.velocityTracker.getXVelocity(this.pointerId);
            } else {
                throw new IllegalArgumentException("Cannot assess fling for ANY orientation");
            }

            if (this.velocityTracker != null) {
                this.velocityTracker.recycle();
                this.velocityTracker = null;
            }
            if (Math.abs(delta) <= 20 || Math.abs(findPointerIndex) <= this.snapVelocity) {
                return Sign.SIGN_ZERO;
            }
            if (findPointerIndex > 0) {
                return Sign.SIGN_NEGATIVE;
            }
            return Sign.SIGN_POSITIVE;
        }

        public final void storeEPU(MotionEvent motionEvent) {
            int action = (motionEvent.getAction() >> 8) & 255;
            if (motionEvent.getPointerId(action) == this.pointerId) {
                action = action == 0 ? 1 : 0;
                this.eX = motionEvent.getX(action);
                this.eY = motionEvent.getY(action);
                this.lEX = this.eX;
                this.lEY = this.eY;
                this.pointerId = motionEvent.getPointerId(action);
                if (this.velocityTracker != null) {
                    this.velocityTracker.clear();
                }
            }
        }

        public final int calc(int x, int y) {
            if (this.fraction != 0.0f) {
                return (int) ((((double) x) * Math.cos(this.fraction)) + (((double) y) * Math.sin(this.fraction)));
            }
            return y;
        }
    }

    static final class PlayerViewClickListener implements View.OnClickListener {
        private final LWWLVideo mLayout;

        public PlayerViewClickListener(LWWLVideo layout) {
            this.mLayout = layout;
        }

        @Suppress(names = "unused")
        public final void onClick(View view) {
            if (this.mLayout.mListener != null) {
                this.mLayout.mListener.WWL_onClicked();
            }
        }
    }
}
