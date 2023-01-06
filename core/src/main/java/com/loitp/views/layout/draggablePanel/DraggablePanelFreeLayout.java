package com.loitp.views.layout.draggablePanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.loitp.R;

import org.jetbrains.annotations.NotNull;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class DraggablePanelFreeLayout extends ViewGroup {
    private final String logTag = getClass().getSimpleName();
    private final ViewDragHelper mDragHelper;
    private View mHeaderView;
    private View mDescView;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private int mDragRange;
    private int mTop;
    private float mDragOffset;

    public DraggablePanelFreeLayout(Context context) {
        this(context, null);
    }

    public DraggablePanelFreeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeaderView = findViewById(R.id.view_header);
        mDescView = findViewById(R.id.view_desc);
    }

    public DraggablePanelFreeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
    }

    public void minimize() {
        smoothSlideTo(1f);
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
        public boolean tryCaptureView(@NotNull View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewPositionChanged(@NotNull View changedView, int left, int top, int dx, int dy) {
            mTop = top;
            mDragOffset = (float) top / mDragRange;
//            Log.d(logTag, "onViewPositionChanged " + left + ", " + top + ", " + dx + ", " + dy + ", " + mDragOffset);
            if (mDragOffset == 0f) {
                if (mState != State.TOP) {
                    mState = State.TOP;
                    if (callback != null) {
                        callback.onStateChange(mState);
                    }
//                    Log.d(logTag, "State.TOP");
                }
            } else if (mDragOffset == 1f) {
                if (mState != State.BOTTOM) {
                    mState = State.BOTTOM;
                    if (callback != null) {
                        callback.onStateChange(mState);
                    }
//                    Log.d(logTag, "State.BOTTOM");
                }
            } else {
                if (mState != State.MID) {
                    mState = State.MID;
                    if (callback != null) {
                        callback.onStateChange(mState);
                    }
//                    Log.d(logTag, "State.MID");
                }
            }
            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);
            mDescView.setAlpha(1 - mDragOffset);
            requestLayout();
        }

        @Override
        public void onViewReleased(@NotNull View releasedChild, float xvel, float yvel) {
//            Log.d(logTag, "onViewReleased");
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top);
        }

        @Override
        public int getViewVerticalDragRange(@NotNull View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(@NotNull View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();
            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public int clampViewPositionHorizontal(@NotNull View child, int left, int dx) {
            //Log.d(TAG, "clampViewPositionHorizontal " + left + "," + dx);
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mHeaderView.getWidth();
            return Math.min(Math.max(left, leftBound), rightBound);
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
//                Log.d(logTag, "onInterceptTouchEvent ACTION_DOWN");
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
//                Log.d(logTag, "onInterceptTouchEvent ACTION_MOVE");
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);
        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();
        boolean isHeaderViewUnder = mDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
//                Log.d(logTag, "onTouchEvent ACTION_DOWN");
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (isPositionBottom()) {
//                    "onTouchEvent ACTION_UP no slide"
                } else {
                    if (mDragOffset < 0.5f) {
                        maximize();
                    } else {
                        minimize();
                    }
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
        //Log.d(TAG, "onLayout " + l + ", " + t + ", " + r + ", " + b);
        mDragRange = getHeight() - mHeaderView.getHeight();
        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());
        mDescView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }

    public enum State {TOP, BOTTOM, MID}

    private State mState;

    @Suppress(names = "unused")
    private boolean isPositionTop() {
        return mState == State.TOP;
    }

    private boolean isPositionBottom() {
        return mState == State.BOTTOM;
    }

    @Suppress(names = "unused")
    private boolean isPositionMid() {
        return mState == State.MID;
    }

    public interface Callback {
        void onStateChange(State state);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
