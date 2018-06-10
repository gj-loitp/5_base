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

/**
 * Created by Administrator on 2018/5/6.
 */

public class ViewDragHelperView extends LinearLayout {
    private View mDragView;         //随意拖动View
    private View mAutoBackView;    // 拖动释放后返回原位置
    private View mEdgeTrackerView; // 从边缘触发拖动

    private ViewDragHelper mViewDragHelper;

    private int mAutoBackViewX;
    private int mAutoBackViewY;

    public ViewDragHelperView(@NonNull Context context) {
        this(context, null);
    }

    public ViewDragHelperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    // STEP 1:初始 mViewDragHelper
    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, callback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT); // 左边缘拖动 调用 onEdgeDragStarted 里传入的View
    }

    // SETP 2:拿到回调参数callBack
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override  //哪一个View可以被拖动
        public boolean tryCaptureView(View child, int pointerId) {
            // mEdgeTrackerView 从边缘触发，禁止拖动
            return mDragView == child || mAutoBackView == child;
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

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (releasedChild == mAutoBackView) {
                mViewDragHelper.settleCapturedViewAt(mAutoBackViewX, mAutoBackViewY);//传入原始的位置左边，让mAutoBackView返回原位置
            }
            invalidate();// 如果没有执行完毕， 就执行computeScroll，继续计算
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mViewDragHelper.captureChildView(mEdgeTrackerView, pointerId);
        }
    };

    //STEP 3:将View 的触摸 和 事件拦截都扔给 mViewDragHelper
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        //固定写法
        //此方法用于自动滚动,比如自动回滚到默认位置.
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackViewX = mAutoBackView.getLeft();
        mAutoBackViewY = mAutoBackView.getTop();
    }
}
