package vn.loitp.app.activity.function.viewdraghelpersimple;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;

public class VDHView extends LinearLayout {
    private final String TAG = getClass().getSimpleName();
    private View headerView;
    private View bodyView;
    private ViewDragHelper mViewDragHelper;
    private int mAutoBackViewX;
    private int mAutoBackViewY;
    //private float mInitialMotionX;
    //private float mInitialMotionY;
    private int mDragRange;
    private float mDragOffset;
    private int screenW;
    private int screenH;
    private boolean isEnableAlpha = true;

    public VDHView(@NonNull Context context) {
        this(context, null);
    }

    public VDHView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VDHView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface Callback {
        public void onStateChange(State state);

        public void onViewPositionChanged(int left, int top, float dragOffset);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    private void initView() {
        screenW = LScreenUtil.getScreenWidth();
        screenH = LScreenUtil.getScreenHeight();
        LLog.d(TAG, "fuck initView screenW x screenH: " + screenW + " x " + screenH);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = findViewById(R.id.header_view);
        bodyView = findViewById(R.id.body_view);
        headerView.post(new Runnable() {
            @Override
            public void run() {
                LLog.d(TAG, "fuck onFinishInflate size headerView: " + headerView.getMeasuredWidth() + "x" + headerView.getMeasuredHeight());
                LLog.d(TAG, "fuck onFinishInflate size bodyView: " + bodyView.getMeasuredWidth() + "x" + bodyView.getMeasuredHeight());
            }
        });
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mDragOffset = (float) top / mDragRange;
            if (mDragOffset > 1) {
                mDragOffset = 1;
            }
            if (callback != null) {
                callback.onViewPositionChanged(left, top, mDragOffset);
            }
            LLog.d(TAG, "onViewPositionChanged left: " + left + ", top: " + top + " -> mDragOffset: " + mDragOffset);
            if (mDragOffset == 0) {
                if (state != State.TOP) {
                    state = State.TOP;
                    if (callback != null) {
                        callback.onStateChange(state);
                    }
                }
            } else if (mDragOffset == 1) {
                if (state != State.BOTTOM) {
                    state = State.BOTTOM;
                    if (callback != null) {
                        callback.onStateChange(state);
                    }
                }
            } else {
                if (state != State.MID) {
                    state = State.MID;
                    if (callback != null) {
                        callback.onStateChange(state);
                    }
                }
            }
            int x = 0;
            int y = headerView.getHeight() + top;
            bodyView.layout(x, y, x + bodyView.getMeasuredWidth(), y + bodyView.getMeasuredHeight());
            if (isEnableAlpha) {
                bodyView.setAlpha(1 - mDragOffset / 2);
            } else {
                if (bodyView.getAlpha() != 1f) {
                    bodyView.setAlpha(1f);
                }
            }
            headerView.setPivotX(headerView.getWidth());
            headerView.setPivotY(headerView.getHeight());
            headerView.setScaleX(1 - mDragOffset / 2);
            headerView.setScaleY(1 - mDragOffset / 2);
            //requestLayout();
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return headerView == child;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int rangeY = getHeight() - child.getHeight();
            if (top <= 0) {
                return 0;
            } else if (top > rangeY) {
                return rangeY;
            } else {
                return top;
            }
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            int rangeX = getWidth() - child.getWidth();
            if (left <= 0) {
                return 0;
            } else if (left > rangeX) {
                return rangeX;
            } else {
                return left;
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == headerView && isAutoBackToOriginalPosition) {
                mViewDragHelper.settleCapturedViewAt(mAutoBackViewX, mAutoBackViewY);
            }
            invalidate();
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        final float x = event.getX();
        final float y = event.getY();
        boolean isViewUnder = mViewDragHelper.isViewUnder(headerView, (int) x, (int) y);
        LLog.d(TAG, "onTouchEvent isViewUnder: " + isViewUnder);
        /*switch (event.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                LLog.d(TAG, "onTouchEvent ACTION_DOWN touch:" + x + " x " + y);
                LLog.d(TAG, "onTouchEvent ACTION_DOWN isViewUnder:" + isViewUnder);
                break;
            }
            case MotionEvent.ACTION_UP: {
                LLog.d(TAG, "onTouchEvent ACTION_UP isViewUnder:" + isViewUnder);
                break;
            }
        }*/
        return isViewUnder;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mDragRange = getHeight() - headerView.getHeight();
        mAutoBackViewX = headerView.getLeft();
        mAutoBackViewY = headerView.getTop();
        LLog.d(TAG, "onLayout l:" + l + ", t:" + t + ", r:" + r + ", b:" + b + ", mAutoBackViewX: " + mAutoBackViewX + ", mAutoBackViewY: " + mAutoBackViewY);
    }

    public enum State {TOP, BOTTOM, MID}

    private State state;

    private void isPositionTop() {
        state = State.TOP;
    }

    private void isPositionBottom() {
        state = State.BOTTOM;
    }

    private void isPositionMid() {
        state = State.MID;
    }

    private boolean isAutoBackToOriginalPosition;

    public boolean isAutoBackToOriginalPosition() {
        return isAutoBackToOriginalPosition;
    }

    public void setAutoBackToOriginalPosition(boolean autoBackToOriginalPosition) {
        isAutoBackToOriginalPosition = autoBackToOriginalPosition;
    }

    public void maximize() {
        if (mViewDragHelper.smoothSlideViewTo(headerView, 0, 0)) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
        }
    }

    public void minimize() {
        if (mViewDragHelper.smoothSlideViewTo(headerView, screenW - headerView.getMeasuredWidth(), screenH - headerView.getMeasuredHeight())) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
        }
    }

    public void smoothSlideTo(int positionX, int positionY) {
        if (mViewDragHelper.smoothSlideViewTo(headerView, positionX, positionY)) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
        }
    }

    public boolean isEnableAlpha() {
        return isEnableAlpha;
    }

    public void setEnableAlpha(boolean enableAlpha) {
        isEnableAlpha = enableAlpha;
    }

    public void toggleShowHideHeaderView() {
        if (headerView.getVisibility() == VISIBLE) {
            headerView.setVisibility(INVISIBLE);
        } else {
            headerView.setVisibility(VISIBLE);
        }
    }

    public void toggleShowHideBodyView() {
        if (bodyView.getVisibility() == VISIBLE) {
            bodyView.setVisibility(INVISIBLE);
        } else {
            bodyView.setVisibility(VISIBLE);
        }
    }
}
