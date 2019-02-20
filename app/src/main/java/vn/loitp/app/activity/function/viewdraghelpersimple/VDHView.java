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

    private void initView() {
        screenW = LScreenUtil.getScreenWidth();
        screenH = LScreenUtil.getScreenHeight();
        LLog.d(TAG, "fuck initView screenW x screenH: " + screenW + " x " + screenH);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
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

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mDragOffset = (float) top / mDragRange;
            LLog.d(TAG, "onViewPositionChanged left: " + left + ", top: " + top + " -> mDragOffset: " + mDragOffset);

            int x = 0;
            int y = headerView.getHeight() + top;
            bodyView.layout(x, y, x + bodyView.getMeasuredWidth(), y + bodyView.getMeasuredHeight());
            bodyView.setAlpha(1 - mDragOffset);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return headerView == child;
        }

        /*@Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }*/

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
        //LLog.d(TAG, "onInterceptTouchEvent");
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //LLog.d(TAG, "onTouchEvent");
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

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action != MotionEvent.ACTION_DOWN) {
            mViewDragHelper.cancel();
            LLog.d(TAG, "onInterceptTouchEvent return super.onInterceptTouchEven");
            return super.onInterceptTouchEvent(ev);
        }
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            LLog.d(TAG, "onInterceptTouchEvent return false");
            return false;
        }
        final float x = ev.getX();
        final float y = ev.getY();
        boolean interceptTap = false;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                //LLog.d(TAG, "onInterceptTouchEvent ACTION_DOWN");
                mInitialMotionX = x;
                mInitialMotionY = y;
                interceptTap = mViewDragHelper.isViewUnder(headerView, (int) x, (int) y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //LLog.d(TAG, "onInterceptTouchEvent ACTION_MOVE");
                final float adx = Math.abs(x - mInitialMotionX);
                final float ady = Math.abs(y - mInitialMotionY);
                final int slop = mViewDragHelper.getTouchSlop();
                if (ady > slop && adx > ady) {
                    mViewDragHelper.cancel();
                    LLog.d(TAG, "onInterceptTouchEvent ACTION_MOVE return false");
                    return false;
                }
            }
        }
        LLog.d(TAG, "onInterceptTouchEvent mViewDragHelper.shouldInterceptTouchEvent(ev) || interceptTap");
        return mViewDragHelper.shouldInterceptTouchEvent(ev) || interceptTap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mViewDragHelper.processTouchEvent(ev);
        final int action = ev.getAction();
        final float x = ev.getX();
        final float y = ev.getY();
        boolean isHeaderViewUnder = mViewDragHelper.isViewUnder(headerView, (int) x, (int) y);
        //LLog.d(TAG, "onTouchEvent isHeaderViewUnder: " + isHeaderViewUnder);
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                LLog.d(TAG, "onTouchEvent ACTION_DOWN");
                break;
            }
            case MotionEvent.ACTION_UP: {
                LLog.d(TAG, "onTouchEvent ACTION_UP");
                *//*if (mDragOffset < 0.5f) {
                    smoothSlideTo(0f);
                } else {
                    smoothSlideTo(1f);
                }*//*
                break;
            }
        }
        LLog.d(TAG, "onTouchEvent: " + (isHeaderViewUnder && isViewHit(headerView, (int) x, (int) y) || isViewHit(bodyView, (int) x, (int) y)));
        return isHeaderViewUnder && isViewHit(headerView, (int) x, (int) y) || isViewHit(bodyView, (int) x, (int) y);
    }*/

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;
        return screenX >= viewLocation[0] && screenX < viewLocation[0] + view.getWidth() && screenY >= viewLocation[1] && screenY < viewLocation[1] + view.getHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mDragRange = getHeight() - headerView.getHeight();
        mAutoBackViewX = headerView.getLeft();
        mAutoBackViewY = headerView.getTop();
        LLog.d(TAG, "onLayout l:" + l + ", t:" + t + ", r:" + r + ", b:" + b + ", mAutoBackViewX: " + mAutoBackViewX + ", mAutoBackViewY: " + mAutoBackViewY);
    }

    private enum State {TOP, BOTTOM, MID}

    private State state;

    private void isPositionTop() {
        //LLog.d(TAG, "onViewPositionChanged TOP");
        state = State.TOP;
    }

    private void isPositionBottom() {
        //LLog.d(TAG, "onViewPositionChanged BOTTOM");
        state = State.BOTTOM;
    }

    private void isPositionMid() {
        //LLog.d(TAG, "onViewPositionChanged MID");
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
        mViewDragHelper.smoothSlideViewTo(headerView, 0, 0);
    }

    public void minimize() {
        mViewDragHelper.smoothSlideViewTo(headerView, screenW - headerView.getWidth(), screenH - headerView.getHeight());
    }
}
