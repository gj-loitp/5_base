package vn.loitp.app.activity.function.viewdraghelpersimple;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
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
    private boolean isEnableRevertMaxSize = true;
    private boolean isMinimized;//header view is scaled at least 1
    private int sizeWHeaderViewOriginal;
    private int sizeHHeaderViewOriginal;
    private int sizeWHeaderViewMin;
    private int sizeHHeaderViewMin;
    private int newSizeWHeaderView;
    private int newSizeHHeaderView;
    private int mTop;
    private int mLeft;

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
        headerView.post(new Runnable() {
            @Override
            public void run() {
                sizeWHeaderViewOriginal = headerView.getMeasuredWidth();
                sizeHHeaderViewOriginal = headerView.getMeasuredHeight();
                sizeWHeaderViewMin = sizeWHeaderViewOriginal / 2;
                sizeHHeaderViewMin = sizeHHeaderViewOriginal / 2;
                LLog.d(TAG, "fuck size original: " + sizeWHeaderViewOriginal + "x" + sizeHHeaderViewOriginal + " -> " + sizeWHeaderViewMin + "x" + sizeHHeaderViewMin);
            }
        });
    }

    private ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mLeft = left;
            mTop = top;
            if (mDragOffset == (float) top / mDragRange) {
                return;
            } else {
                mDragOffset = (float) top / mDragRange;
            }
            if (mDragOffset > 1) {
                mDragOffset = 1;
            }
            if (mDragOffset < 0) {
                mDragOffset = 0;
            }
            if (callback != null) {
                callback.onViewPositionChanged(left, top, mDragOffset);
            }
            LLog.d(TAG, "onViewPositionChanged left: " + left + ", top: " + top + " -> mDragOffset: " + mDragOffset + " -> center: " + (left + headerView.getWidth() / 2));

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

            if (isMinimized) {
                if (isEnableRevertMaxSize) {
                    headerView.setPivotX(headerView.getWidth() / 2f);
                    headerView.setPivotY(headerView.getHeight());
                    headerView.setScaleX(1 - mDragOffset / 2);
                    headerView.setScaleY(1 - mDragOffset / 2);
                } else {

                }
            } else {
                headerView.setPivotX(headerView.getWidth() / 2f);
                headerView.setPivotY(headerView.getHeight());
                headerView.setScaleX(1 - mDragOffset / 2);
                headerView.setScaleY(1 - mDragOffset / 2);
            }

            newSizeWHeaderView = (int) (sizeWHeaderViewOriginal * headerView.getScaleX());
            newSizeHHeaderView = (int) (sizeHHeaderViewOriginal * headerView.getScaleY());
            //LLog.d(TAG, "newSizeW " + newSizeWHeaderView + "x" + newSizeHHeaderView);

            if (mDragOffset == 0) {
                //top_left, top, top_right
                if (left <= -headerView.getWidth() / 2) {
                    changeState(State.TOP_LEFT);
                } else if (left >= headerView.getWidth() / 2) {
                    changeState(State.TOP_RIGHT);
                } else {
                    changeState(State.TOP);
                }
            } else if (mDragOffset == 1) {
                //bottom_left, bottom, bottom_right
                if (left <= -headerView.getWidth() / 2) {
                    changeState(State.BOTTOM_LEFT);
                } else if (left >= headerView.getWidth() / 2) {
                    changeState(State.BOTTOM_RIGHT);
                } else {
                    changeState(State.BOTTOM);
                }
                isMinimized = true;
            } else {
                //mid_left, mid, mid_right
                if (left <= -headerView.getWidth() / 2) {
                    changeState(State.MID_LEFT);
                } else if (left >= headerView.getWidth() / 2) {
                    changeState(State.MID_RIGHT);
                } else {
                    changeState(State.MID);
                }
            }
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return headerView == child;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            int minY;
            if (isEnableRevertMaxSize) {
                minY = -child.getHeight() / 2;
            } else {
                minY = -child.getHeight() * 3 / 2;
            }
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
            LLog.d(TAG, "changeState: " + newState);
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
        //LLog.d(TAG, "onTouchEvent isViewUnder: " + isViewUnder);
        switch (event.getAction() & MotionEventCompat.ACTION_MASK) {
            /*case MotionEvent.ACTION_DOWN: {
                LLog.d(TAG, "onTouchEvent ACTION_DOWN touch:" + x + " x " + y);
                LLog.d(TAG, "onTouchEvent ACTION_DOWN isViewUnder:" + isViewUnder);
                break;
            }*/
            case MotionEvent.ACTION_UP: {
                /*int mCenterX = mLeft + headerView.getWidth() / 2;
                LLog.d(TAG, "fuck onTouchEvent ACTION_UP state:" + state.name() + ", mLeft: " + mLeft + ", mTop: " + mTop + ", mCenterX: " + mCenterX);
                if (state == State.TOP_LEFT || state == State.TOP_RIGHT || state == State.BOTTOM_LEFT || state == State.BOTTOM_RIGHT) {
                    //TODO iplm
                    LLog.d(TAG, "fuck destroy state: " + state.name());
                } else if (state == State.TOP) {
                    if (isEnableRevertMaxSize) {
                        maximize();
                    } else {
                        if (isMinimized) {
                            if (mCenterX <= getWidth() / 2) {
                                //left part
                                minimizeTopLeft();
                            } else {
                                //right part
                                minimizeTopRight();
                            }
                        }
                    }
                } else if (state == State.BOTTOM) {
                    if (mCenterX <= getWidth() / 2) {
                        //left part
                        minimizeBottomLeft();
                    } else {
                        //right part
                        minimizeBottomRight();
                    }
                } else {
                    int mCenterY = mTop + headerView.getHeight() / 2;
                    if (mCenterX <= getWidth() / 2) {
                        if (mCenterY <= getHeight() / 2) {
                            if (isEnableRevertMaxSize) {
                                maximize();
                            } else {
                                if (isMinimized) {
                                    //top left
                                    minimizeTopLeft();
                                }
                            }
                        } else {
                            //bottom left
                            minimizeBottomLeft();
                        }
                    } else {
                        if (mCenterY <= getHeight() / 2) {
                            if (isEnableRevertMaxSize) {
                                maximize();
                            } else {
                                if (isMinimized) {
                                    //top right
                                    minimizeTopRight();
                                }
                            }
                        } else {
                            //bottom right
                            minimizeBottomRight();
                        }
                    }
                }
                break;*/
            }
        }
        return isViewUnder;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mDragRange = getHeight() - headerView.getHeight();
        mAutoBackViewX = headerView.getLeft();
        mAutoBackViewY = headerView.getTop();
        //LLog.d(TAG, "onLayout l:" + l + ", t:" + t + ", r:" + r + ", b:" + b + ", mAutoBackViewX: " + mAutoBackViewX + ", mAutoBackViewY: " + mAutoBackViewY);
    }

    public enum State {TOP, TOP_LEFT, TOP_RIGHT, BOTTOM, BOTTOM_LEFT, BOTTOM_RIGHT, MID, MID_LEFT, MID_RIGHT}

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
        if (isEnableRevertMaxSize) {
            smoothSlideTo(0, 0);
        } else {
            Log.e(TAG, "Error: cannot maximize because isEnableRevertMaxSize is true");
        }
    }

    public void minimizeBottomLeft() {
        int posX = getWidth() - sizeWHeaderViewOriginal - sizeWHeaderViewMin / 2;
        int posY = getHeight() - sizeHHeaderViewOriginal;
        //LLog.d(TAG, "minimize " + posX + "x" + posY);
        smoothSlideTo(posX, posY);
    }

    public void minimizeBottomRight() {
        int posX = getWidth() - sizeWHeaderViewOriginal + sizeWHeaderViewMin / 2;
        int posY = getHeight() - sizeHHeaderViewOriginal;
        //LLog.d(TAG, "minimize " + posX + "x" + posY);
        smoothSlideTo(posX, posY);
    }

    public void minimizeTopRight() {
        if (isEnableRevertMaxSize) {
            Log.e(TAG, "Error: cannot minimizeTopRight because isEnableRevertMaxSize is true");
            return;
        }
        if (!isMinimized) {
            Log.e(TAG, "Error: cannot minimizeTopRight because isMinimized is false. This function only works if the header view is scrolled BOTTOM");
            return;
        }
        int posX = getWidth() - sizeWHeaderViewOriginal + sizeWHeaderViewMin / 2;
        int posY = -sizeHHeaderViewOriginal * 2;
        LLog.d(TAG, "fuck minimizeTopRight " + posX + "x" + posY);
        smoothSlideTo(posX, posY);
    }

    public void minimizeTopLeft() {

    }

    public void smoothSlideTo(int positionX, int positionY) {
        if (mViewDragHelper.smoothSlideViewTo(headerView, positionX, positionY)) {
            ViewCompat.postInvalidateOnAnimation(this);
            postInvalidate();
        }
    }

    public boolean isMinimized() {
        return isMinimized;
    }

    public boolean isEnableAlpha() {
        return isEnableAlpha;
    }

    public void setEnableAlpha(boolean enableAlpha) {
        isEnableAlpha = enableAlpha;
    }

    public boolean isEnableRevertMaxSize() {
        return isEnableRevertMaxSize;
    }

    public void setEnableRevertMaxSize(boolean enableRevertMaxSize) {
        isEnableRevertMaxSize = enableRevertMaxSize;
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
