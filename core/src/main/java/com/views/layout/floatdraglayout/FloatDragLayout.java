package com.views.layout.floatdraglayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

/**
 * DragLayout 自适应多种模式下的悬浮按钮支持，并且支持位置状态恢复
 *
 * @author baishixian
 * @date 2017/12/7 下午2:53
 */
//TODO convert kotlin
public class FloatDragLayout extends FrameLayout {

    public static final String TAG = "FloatDragLayout";

    /**
     * 点击事件时允许的偏移距离
     */
    private final int OFFSET_ALLOW_DISTANCE = 10;

    /**
     * 记录最近一次触摸点的坐标
     */
    private float mLastTouchPointX;
    private float mLastTouchPointY;

    /**
     * 拖动事件标记
     */
    private boolean isDragAction;

    /**
     * 拖动完成后是否自动贴靠屏幕边缘
     */
    private boolean isNearScreenEdge = true;

    private int mScreenContentWidth;
    /**
     * 屏幕显示高度（不包括虚拟按键高度）
     */
    private int mScreenContentHeight;
    /**
     * 状态栏高度
     */
    private int mStatusHeight;

    /**
     * 虚拟按键像素高度
     */
    private int mNavigationBarHeight;

    /**
     * 屏幕物理尺寸像素高度
     */
    private int mScreenHardHeight;

    /**
     * 屏幕物理尺寸像素宽度
     */
    private int mScreenHardWidth;

    public FloatDragLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public FloatDragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatDragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 一些初始化工作
     */
    private void init(Context context) {
        mScreenContentWidth = DisplayUtil.getScreenContentWidth(context);
        mScreenContentHeight = DisplayUtil.getScreenContentHeight(context);
        mStatusHeight = DisplayUtil.getStatusHeight(context);
        mNavigationBarHeight = DisplayUtil.getNavigationBarHeight(context);
        mScreenHardHeight = DisplayUtil.getScreenHardwareHeight(context);
        mScreenHardWidth = DisplayUtil.getScreenHardwareWidth(context);
        Log.d(TAG, "FloatDragLayout init mScreenContentWidth=" + mScreenContentWidth + ", mScreenContentHeight=" + mScreenContentHeight + ", mScreenHardWidth=" + mScreenHardWidth + ",mScreenHardHeight=" + mScreenHardHeight + ",StatusHeight=" + mStatusHeight + ",mNavigationBarHeight" + mNavigationBarHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 设置触摸事件拦截，事件不再向其子视图传递，直接交由下面的 onTouchEvent() 处理
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 获取触摸点相对于屏幕的坐标值
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        mScreenContentWidth = DisplayUtil.getScreenContentWidth(getContext());
        mScreenContentHeight = DisplayUtil.getScreenContentHeight(getContext());

        // 兼容处理多点触控的情况
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                // 要求其父布局 (ViewGroup) 不要使用 onInterceptTouchEvent(MotionEvent) 拦截触摸事件
                getParent().requestDisallowInterceptTouchEvent(true);

                // 记录最近一次触摸点的坐标
                mLastTouchPointX = rawX;
                mLastTouchPointY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                // 计算拖动距离（当前触摸点的坐标 - 上一次触摸点的坐标）
                float distanceX = rawX - mLastTouchPointX;
                float distanceY = rawY - mLastTouchPointY;

                // 计算前后两次触摸点的距离
                int distance = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                // 点击事件的偏移修正，消除"点击抖动"
                if (distance >= OFFSET_ALLOW_DISTANCE) {

                    // 对于超过偏移允许范围的情况，认定为拖动事件
                    isDragAction = true;

                    // 计算坐标值变更 (布局内)
                    View parent = (View) getParent();
                    int parentHeight = parent.getHeight();
                    int parentWidth = parent.getWidth();

                    if (parentHeight + mStatusHeight >= mScreenContentHeight) {
                        parentHeight = mScreenContentHeight;
                    }

                    float x = getX() + distanceX;
                    float y = getY() + distanceY;

                    // 检测是否到达父view边缘 左上右下（顶部以状态栏为界）
                    distanceX = x < 0 ? 0 : x > parentWidth - getWidth() ? 0 : distanceX;
                    distanceY = y < 0 ? 0 : y + getHeight() > parentHeight ? 0 : distanceY;

                    // 拖拽时进行视图位置移动
                    ViewCompat.offsetLeftAndRight(this, (int) distanceX);
                    ViewCompat.offsetTopAndBottom(this, (int) distanceY);

                    // 更新最近一次触摸点的坐标
                    mLastTouchPointX = rawX;
                    mLastTouchPointY = rawY;
                }
                break;
            case MotionEvent.ACTION_UP:
                // 如果是拖动行为，
                if (isDragAction) {
                    // 恢复到未按压效果
                    setPressed(false);
                    // 恢复拖动标记状态
                    isDragAction = false;

                    //这里需设置LayoutParams，不然按home后回再到页面等view会回到原来的地方
                    updateLayoutParams(getLeft(), getTop(), getRight(), getBottom());

                    // 如果滑动完成后要自动贴合屏幕边缘
                    if (isNearScreenEdge) {

                        // 计算坐标值变更 (布局内)
                        View parent = (View) getParent();
                        int parentWidth = parent.getWidth();
                        int parentHeight = parent.getHeight();
                        int bottomY = parentHeight - getHeight();

                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            if (parentHeight < mScreenHardHeight && parentHeight + mStatusHeight >= mScreenContentHeight) {
                                parentHeight = mScreenContentHeight;
                                bottomY = parentHeight - getHeight() - mStatusHeight;
                            }
                        } else {
                            if (parentHeight == mScreenHardHeight) {
                                parentHeight = mScreenContentHeight;
                                bottomY = parentHeight - getHeight();
                            } else {
                                if (parentHeight + mStatusHeight >= mScreenContentHeight) {
                                    parentHeight = mScreenContentHeight;
                                    bottomY = parentHeight - getHeight() - mStatusHeight;
                                }
                            }
                        }

                        // 距离 top 边线的距离
                        float topDistance = getY();
                        // 距离 bottom 边线的距离
                        float bottomDistance = parentHeight - getY() - getHeight();
                        // 距离 left 边线的距离
                        float leftDistance = getX();
                        // 距离 right 边线的距离
                        float rightDistance = parentWidth - getX() - getWidth();

                        // 找到x轴上离边线的最近距离
                        float xMinDistance = leftDistance <= rightDistance ? leftDistance : rightDistance;
                        // 找到y轴上离边线的最近距离
                        float yMinDistance = topDistance <= bottomDistance ? topDistance : bottomDistance;

                        ObjectAnimator moveToEdgeAnimator;
                        // 根据距离判断移动方向
                        if (xMinDistance <= yMinDistance) {
                            // 横向拖动超过父View一半时，该贴靠到屏幕右侧
                            if (getX() > parentWidth / 2) {
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "x", leftDistance, parentWidth - getWidth());
                            } else { // 否则贴靠屏幕左侧
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "x", leftDistance, 0);
                            }
                        } else {
                            // 竖向拖动超过父View一半时，该贴靠到屏幕下侧
                            if (getY() > parentHeight / 2) {
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "y", topDistance, bottomY);
                            } else { // 否则贴靠屏幕上侧
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "y", topDistance, 0);
                            }
                        }
                        moveToEdgeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                        moveToEdgeAnimator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                updateLayoutParams(getLeft(), getTop(), getRight(), getBottom());
                            }
                        });
                        moveToEdgeAnimator.setDuration(600);
                        moveToEdgeAnimator.start();
                    }
                    isDragAction = false;
                }
                break;
            default:
                break;
        }
        // 用于处理点击事件，click 判断在父类的 onTouchEvent 中，所以这里调用 super.onTouchEvent(event) 是要确保父类有正常处理到 DOWN 和 UP 事件，避免出现视图可拖动但点击无效
        // 一个 click 触发必须同时包含 DOWN 和 UP 事件（成对出现），所以也可以分别放置到上面 DOWN 和 UP 对应的条件中调用
        super.onTouchEvent(event);
        // 事件处理完成，停止事件传递，这样该事件序列的后续事件（Move、Up）也是让其处理
        return true;
    }

    private void updateLayoutParams(int left, int top, int right, int bottom) {
        LayoutParams layoutParams = new LayoutParams(getWidth(), getHeight());
        layoutParams.setMargins(left, top, right, bottom);
        setLayoutParams(layoutParams);
    }

    public void setNearScreenEdge(boolean nearScreenEdge) {
        isNearScreenEdge = nearScreenEdge;
    }

    /******************** 视图状态保存和恢复 *******************/
    @Override
    protected Parcelable onSaveInstanceState() {
        Log.d(TAG, "onSaveInstanceState");
        FloatDragLayout.SavedViewState state = new FloatDragLayout.SavedViewState(super.onSaveInstanceState());
        state.lastTouchPointX = mLastTouchPointX;
        state.lastTouchPointY = mLastTouchPointY;
        state.isDragAction = isDragAction;
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Log.d(TAG, "onRestoreInstanceState");
        if (!(state instanceof FloatDragLayout.SavedViewState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        FloatDragLayout.SavedViewState ss = (FloatDragLayout.SavedViewState) state;
        super.onRestoreInstanceState(ss);

        mLastTouchPointX = ss.lastTouchPointX;
        mLastTouchPointY = ss.lastTouchPointY;
        isDragAction = ss.isDragAction;
    }

    static class SavedViewState extends BaseSavedState {
        float lastTouchPointX;
        float lastTouchPointY;
        boolean isDragAction;

        SavedViewState(Parcelable superState) {
            super(superState);
        }

        private SavedViewState(Parcel source) {
            super(source);
            lastTouchPointX = source.readFloat();
            lastTouchPointY = source.readFloat();
            isDragAction = source.readByte() == (byte) 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(lastTouchPointX);
            out.writeFloat(lastTouchPointY);
            out.writeByte(isDragAction ? (byte) 1 : (byte) 0);
        }

        public static final Creator<SavedViewState> CREATOR = new Creator<SavedViewState>() {
            @Override
            public FloatDragLayout.SavedViewState createFromParcel(Parcel source) {
                return new FloatDragLayout.SavedViewState(source);
            }

            @Override
            public FloatDragLayout.SavedViewState[] newArray(int size) {
                return new FloatDragLayout.SavedViewState[size];
            }
        };

    }

}
