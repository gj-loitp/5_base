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
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = findViewById(R.id.header_view);
        bodyView = findViewById(R.id.body_view);
        /*headerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.showShort(getContext(), "Size: " + view.getWidth() + " x " + view.getHeight());
            }
        });*/
        /*headerView.post(new Runnable() {
            @Override
            public void run() {
                LLog.d(TAG, "onFinishInflate size headerView: " + headerView.getMeasuredWidth() + "x" + headerView.getMeasuredHeight());
                LLog.d(TAG, "onFinishInflate size bodyView: " + bodyView.getMeasuredWidth() + "x" + bodyView.getMeasuredHeight());
            }
        });*/
    }

    //private boolean isLeftPart;
    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (mDragOffset == (float) top / mDragRange) {
                return;
            } else {
                mDragOffset = (float) top / mDragRange;
            }
            if (mDragOffset > 1) {
                mDragOffset = 1;
            }
            if (callback != null) {
                callback.onViewPositionChanged(left, top, mDragOffset);
            }
            LLog.d(TAG, "onViewPositionChanged left: " + left + ", top: " + top + " -> mDragOffset: " + mDragOffset + " -> center: " + (left + headerView.getWidth() / 2));
            if (mDragOffset == 0) {
                //top
                if (left == 0) {
                    changeState(State.TOP_LEFT);
                } else if (left == getWidth() - headerView.getWidth()) {
                    changeState(State.TOP_RIGHT);
                } else {
                    changeState(State.TOP);
                }
            } else if (mDragOffset == 1) {
                //bottom
                if (left == 0) {
                    changeState(State.BOTTOM_LEFT);
                } else if (left == getWidth() - headerView.getWidth()) {
                    changeState(State.BOTTOM_RIGHT);
                } else {
                    changeState(State.BOTTOM);
                }
            } else {
                //mid
                if (left == 0) {
                    //isLeftPart = true;
                    changeState(State.LEFT);
                } else if (left == getWidth() - headerView.getWidth()) {
                    //isLeftPart = false;
                    changeState(State.RIGHT);
                } else {
                    /*if (left + headerView.getWidth() / 2 <= getWidth() / 2) {
                        isLeftPart = true;
                    } else {
                        isLeftPart = false;
                    }*/
                    changeState(State.MID);
                }
                //LLog.d(TAG, "fuck isLeftPart: " + isLeftPart);
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

            //float sizeWhenSlide = (1 - mDragOffset / 2) * headerView.getWidth();
            //LLog.d(TAG, "onViewPositionChanged mDragOffset: " + mDragOffset + " -> sizeWhenSlide: " + sizeWhenSlide);

            //work
            headerView.setPivotX(headerView.getWidth() / 2f);
            headerView.setPivotY(headerView.getHeight());
            headerView.setScaleX(1 - mDragOffset / 2);
            headerView.setScaleY(1 - mDragOffset / 2);

            /*if (isLeftPart) {
                headerView.setPivotX(0);
                headerView.setPivotY(headerView.getHeight());
                headerView.setScaleX(1 - mDragOffset / 2);
                headerView.setScaleY(1 - mDragOffset / 2);
            } else {
                headerView.setPivotX(headerView.getWidth());
                headerView.setPivotY(headerView.getHeight());
                headerView.setScaleX(1 - mDragOffset / 2);
                headerView.setScaleY(1 - mDragOffset / 2);
            }*/
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return headerView == child;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int minY = -child.getHeight() / 2;
            float scaledY = child.getScaleY();
            int sizeHScaled = (int) (scaledY * child.getHeight());
            int maxY = getHeight() - sizeHScaled * 3 / 2;
            //LLog.d(TAG, "clampViewPositionVertical getHeight:" + getHeight() + ", scaledY:" + scaledY + ", sizeHScaled: " + sizeHScaled + ", top:" + top + ", minY: " + minY + ", maxY: " + maxY);
            if (top <= minY) {
                return minY;
            } else if (top > maxY) {
                return maxY;
            } else {
                return top;
            }
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            int minX = -child.getWidth() / 2;
            int maxX = getWidth() - child.getWidth() / 2;
            if (left <= minX) {
                return minX;
            } else if (left > maxX) {
                return maxX;
            } else {
                return left;
            }
        }

        /*@Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //return super.clampViewPositionVertical(child, top, dy);
            int rangeY = getHeight() - child.getHeight();
            if (top <= 0) {
                return 0;
            } else if (top > rangeY) {
                return rangeY;
            } else {
                return top;
            }
        }*/

        /*@Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //return super.clampViewPositionHorizontal(child, left, dx);
            int rangeX = getWidth() - child.getWidth();
            if (left <= 0) {
                return 0;
            } else if (left > rangeX) {
                return rangeX;
            } else {
                return left;
            }
        }*/

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

    private void changeState(State newState) {
        if (state != newState) {
            state = newState;
            LLog.d(TAG, "fuck changeState: " + newState);
            if (callback != null) {
                callback.onStateChange(state);
            }
        }
    }

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

    public enum State {TOP, BOTTOM, LEFT, RIGHT, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, MID}

    private State state;

    public State getState() {
        return state;
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
        if (mViewDragHelper.smoothSlideViewTo(headerView, getWidth() - headerView.getMeasuredWidth(), getHeight() - headerView.getMeasuredHeight())) {
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
