package vn.loitp.views.layout.youtubelayout;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/22/2018.
 */

public class YoutubeLayout extends ViewGroup {
    private final String TAG = getClass().getSimpleName();
    private final ViewDragHelper mDragHelper;
    private View mHeaderView;
    private View mDescView;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public YoutubeLayout(Context context) {
        this(context, null);
    }

    public YoutubeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewById(R.id.viewHeader);
        mDescView = findViewById(R.id.viewDesc);
    }

    public YoutubeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    public void maximize() {
        smoothSlideTo(0f);
    }

    private boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);
        if (mDragHelper.smoothSlideViewTo(mHeaderView, mHeaderView.getLeft(), y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            LLog.d(TAG, "onViewPositionChanged " + left + " - " + top + " - " + dx + " - " + dy);
            mTop = top;

            mDragOffset = (float) top / mDragRange;
            //LLog.d(TAG, "onViewPositionChanged mDragOffset " + mDragOffset);
            if (mDragOffset == 0f) {
                isPositionTop();
            } else if (mDragOffset == 1f) {
                isPositionBottom();
            } else {
                isPositionMid();
            }

            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);

            mDescView.setAlpha(1 - mDragOffset);

            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            LLog.d(TAG, "onViewReleased");
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            LLog.d(TAG, "clampViewPositionVertical " + top);
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LLog.d(TAG, "clampViewPositionHorizontal " + left + "," + dx);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mHeaderView.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if ((action != MotionEvent.ACTION_DOWN)) {
            mDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel();
            return false;
        }

        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                LLog.d(TAG, "onInterceptTouchEvent ACTION_DOWN");
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                LLog.d(TAG, "onInterceptTouchEvent ACTION_MOVE");
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mDragHelper.getTouchSlop();
                if (ady > slop && adx > ady) {
                    mDragHelper.cancel();
                    return false;
                }
            }
        }

        return mDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);

        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();

        boolean isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                LLog.d(TAG, "onTouchEvent ACTION_DOWN");
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                LLog.d(TAG, "onTouchEvent ACTION_UP");
                /*final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final int slop = mDragHelper.getTouchSlop();
                if (dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
                    LLog.d(TAG, "if");
                    if (mDragOffset == 0) {
                        smoothSlideTo(1f);
                    } else {
                        smoothSlideTo(0f);
                    }
                } */
                //LLog.d(TAG, "mDragOffset " + mDragOffset);
                if (mDragOffset < 0.5f) {
                    smoothSlideTo(0f);
                } else {
                    smoothSlideTo(1f);
                }
                break;
            }
        }

        return isHeaderViewUnder && isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mDescView, (int) x, (int) y);
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() &&
                screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = getHeight() - mHeaderView.getHeight();
        mHeaderView.layout(
                0,
                mTop,
                r,
                mTop + mHeaderView.getMeasuredHeight());

        mDescView.layout(
                0,
                mTop + mHeaderView.getMeasuredHeight(),
                r,
                mTop + b);
    }

    private enum STATE {TOP, BOTTOM, MID}

    private STATE state;

    private void isPositionTop() {
        //LLog.d(TAG, "onViewPositionChanged TOP");
        state = STATE.TOP;
    }

    private void isPositionBottom() {
        //LLog.d(TAG, "onViewPositionChanged BOTTOM");
        state = STATE.BOTTOM;
    }

    private void isPositionMid() {
        //LLog.d(TAG, "onViewPositionChanged MID");
        state = STATE.MID;
    }
}