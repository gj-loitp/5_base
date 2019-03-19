package vn.loitp.views.layout.floatdraglayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.IntDef;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * FloatDragPopupWindow 悬浮可拖动的 PopupWindow，并且拖动完成后可以自动贴靠屏幕边缘，不使用时自动降低其存在感（隐藏一半、降低透明度）
 * <p>
 * 1. 为在兼容低版本 Unity Editor 打包应用时出现的点击事件透传和视图被覆盖，因此只能弃用原有将布局通过 addView 到 DecorView 实现的 FloatDragLayout ，考虑换用 PopupWindow 重新实现。
 * 2. 使用 WindowManager.addView 也可以实现弹出系统窗口进行全局覆盖，但这种方式需要考虑很多兼容性问题，而且在新版本中也要考虑权限问题，并且要根据当前应用状态手动控制其可见性才行，而 PopupWindow 只是当前应用窗口的子窗口，可跟随其父窗口自动保持一致的可见性控制。因此不推荐使用系统窗口和 addView 的方式实现，最终选择 PopupWindow 实现。
 *
 * @author baishixian
 * @date 2018/1/26 上午10:09
 */

public class FloatDragPopupWindow implements PopupWindow.OnDismissListener, View.OnTouchListener {

    /**
     * 悬浮按钮贴靠屏幕边缘的位置
     * 分别是贴靠上边，下边，左边，右边
     */
    @IntDef({Edge.TOP,
            Edge.BOTTOM,
            Edge.LEFT,
            Edge.RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Edge {
        int TOP = 1;
        int BOTTOM = 2;
        int LEFT = 3;
        int RIGHT = 4;
    }

    /**
     * 当前贴靠屏幕的位置，初始化默认贴靠屏幕左边
     */
    @Edge
    private int currentEdge = Edge.LEFT;

    /**
     * 点击事件时允许的偏移距离 （避免点击时出现视图抖动的情况）
     */
    private static final int OFFSET_ALLOW_DISTANCE = 10;

    /**
     * 屏幕内容显示宽度（不包括虚拟按键高度）
     */
    private int mScreenContentWidth;
    /**
     * 屏幕内容显示高度（不包括虚拟按键高度）
     */
    private int mScreenContentHeight;

    /**
     * 状态栏高度
     */
    private int mStatusHeight;

    /**
     * 屏幕物理尺寸像素高度 （包括虚拟按键高度）
     */
    private int mScreenHardHeight;

    /**
     * 是否是拖动事件的标记
     */
    private boolean isDragAction;

    /**
     * 记录最近一次触摸点的坐标
     */
    private float mLastTouchPointX;
    private float mLastTouchPointY;

    private Activity mActivity;

    /**
     * 用于 PopupWindow 中的内容视图
     */
    private View mContentView;

    /**
     * PopupWindow 宽度，默认 WRAP_CONTENT
     */
    private int mWindowWidth;

    /**
     * PopupWindow 高度，默认 WRAP_CONTENT
     */
    private int mWindowHeight;

    /**
     * PopupWindow 背景资源
     */
    private Drawable mBackgroundDrawable;

    /**
     * 修复 Android 7.0 上系统 API 问题的 PopupWindow
     */
    private FixedPopupWindow mCurrentPopupWindow;

    /**
     * 当前 PopupWindow 的位置，屏幕左上角为左边原点，原点位置不会随着 PopupWindow 移动而改变
     */
    private int mCurrentPopupWindowPositionX;
    private int mCurrentPopupWindowPositionY;

    /**
     * ContentView 点击监听器
     */
    private View.OnClickListener mOnClickListener;


    private FloatDragPopupWindow(Builder builder) {
        this.mActivity = builder.activity;
        this.mContentView = builder.contentView;
        this.mWindowWidth = builder.windowWidth;
        this.mWindowHeight = builder.windowHeight;
        this.mBackgroundDrawable = builder.backgroundDrawable;
        this.mOnClickListener = builder.onClickListener;
        this.mCurrentPopupWindowPositionX = builder.x;
        this.mCurrentPopupWindowPositionY = builder.y;

        // 设置 PopupWindow
        initWindow();
    }

    /**
     * 初始化 PopupWindow
     */
    private void initWindow() {
        // 设置 PopupWindow
        mCurrentPopupWindow = new FixedPopupWindow(mContentView, mWindowWidth, mWindowHeight);
        mCurrentPopupWindow.setFocusable(false);
        mCurrentPopupWindow.setBackgroundDrawable(mBackgroundDrawable);
        mCurrentPopupWindow.setOnDismissListener(this);
        mCurrentPopupWindow.setOutsideTouchable(false);

        // 获取屏幕参数
        mScreenContentWidth = DisplayUtil.getScreenContentWidth(mActivity);
        mScreenContentHeight = DisplayUtil.getScreenContentHeight(mActivity);
        mScreenHardHeight = DisplayUtil.getScreenHardwareHeight(mActivity);
        mStatusHeight = DisplayUtil.getStatusHeight(mActivity);
    }

    /**
     * 展示 PopupWindow
     */
    public void show() {

        if (mCurrentPopupWindow == null) {
            Log.d("FloatDragPopupWindow", "show floatDragPopupWindow, but currentPopupWindow is null.");
            return;
        }

        if (!mActivity.isFinishing() && !isShowing()) {

            // 使用 post 避免父窗口 Activity 未初始化完成时就进行 showAtLocation ，
            // 导致出现 android.view.WindowManager$BadTokenException
            mActivity.getWindow().getDecorView().post(new Runnable() {
                @Override
                public void run() {

                    // showAtLocation 是相对于整个窗口的
                    // 第一个参数是需要一个 WindowToken 用于作为这个子窗口的父容器
                    // 第二个参数是 Gravity ，用于相对于屏幕的位置，比如 Gravity.LEFT|Gravity.BOTTOM
                    // 第三、四个参数是表示相对于屏幕左上角为原点的偏移量的 x,y
                    // 注意：这里设置的 x,y 值会直接影响到后面使用 update 更新 PopupWindow 位置时的相对偏移原点
                    // showAtLocation 设置 x,y 为 0 后 update 方法相对偏移计算时的原点就是屏幕左上角的(0,0)，并且原点不会随着位置变化而改变
                    mCurrentPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(),
                            Gravity.NO_GRAVITY,
                            0, 0);

                    // 更新 PopupWindow 位置，避免位于屏幕左上角，此时传入的参数是相对于原点的偏移量
                    updatePopupWindowPosition(mCurrentPopupWindowPositionX, mCurrentPopupWindowPositionY);

                    // 为 ContentView 设置 OnTouchListener 接收处理拖动事件，
                    // 相当于将用户在 ContentView 上的 "滑动" 事件转化成移动 PopupWindow 位置，
                    mContentView.setOnTouchListener(FloatDragPopupWindow.this);

                    // 为 ContentView 设置点击监听器
                    mContentView.setOnClickListener(mOnClickListener);

                    // 设置初始展示完成后 ContentView 的隐藏效果，降低其存在感
                    reduceTheSenseOfPresence();
                }
            });
        }
    }


    /**
     * 降低存在感，悬浮窗口的内容视图隐藏一半，透明度降低一半，以免碍眼
     */
    public void reduceTheSenseOfPresence() {

        if (mContentView == null) {
            return;
        }

        // 移除已有 Runnable
        mContentView.removeCallbacks(mContentViewHideRunnable);

        // 延迟 1 秒钟进行
        mContentView.postDelayed(mContentViewHideRunnable, 1000);
    }

    /**
     * 悬浮窗口的内容视图隐藏一半，透明度降低一半
     */
    private Runnable mContentViewHideRunnable = new Runnable() {
        @Override
        public void run() {

            // 判断状态才进行
            if (isShowing()) {

                // 先恢复到正常状态
                updateContentLayoutParams(0, 0, 0, 0);

                // 隐藏 ContentView 一半
                int left = 0;
                int top = 0;

                switch (currentEdge) {
                    case Edge.TOP:
                        top = -mContentView.getHeight() / 2;
                        break;
                    case Edge.BOTTOM:
                        top = mContentView.getHeight() / 2;
                        break;
                    case Edge.LEFT:
                        left = -mContentView.getWidth() / 2;
                        break;
                    case Edge.RIGHT:
                        left = mContentView.getWidth() / 2;
                        break;
                    default:
                        break;
                }

                // 通过设置 ContentView 的 margin 来调整视图内容，比如当某一方向的 margin 为负数时会在该方向进行等距离移动
                updateContentLayoutParams(left, top, 0, 0);

                // 调整内容布局的透明度
                mContentView.setAlpha(0.5f);
            }
        }
    };

    /**
     * 恢复存在感，悬浮窗口恢复正常状态
     */
    private void restoreTheSenseOfPresence() {
        mContentView.removeCallbacks(mContentViewHideRunnable);
        updateContentLayoutParams(0, 0, 0, 0);
        mContentView.setAlpha(1.0f);
    }

    /**
     * 判断当前 PopupWindow 是否可见
     *
     * @return 当前 PopupWindow 的可见性
     */
    public boolean isShowing() {
        return mCurrentPopupWindow != null && mCurrentPopupWindow.isShowing();
    }

    /**
     * 隐藏当前 PopupWindow
     */
    public void dismiss() {
        if (mCurrentPopupWindow != null) {
            mCurrentPopupWindow.dismiss();
        }
        mCurrentPopupWindow = null;
    }


    @Override
    public void onDismiss() {
        mCurrentPopupWindow = null;
    }

    /**
     * 获取内容视图
     */
    public View getContentView() {
        return mContentView;
    }

    /**
     * 获取当前窗口相对于屏幕的位置，原点为屏幕左上角
     *
     * @return x 坐标
     */
    public int getCurrentWindowX() {
        return mCurrentPopupWindowPositionX;
    }

    /**
     * 获取当前窗口相对于屏幕的位置，原点为屏幕左上角
     *
     * @return y 坐标
     */
    public int getCurrentWindowY() {
        return mCurrentPopupWindowPositionY;
    }

    /**
     * 处理 ContentView 的触摸事件，将用户在 ContentView 上进行的滑动操作变成看起来是对 PopupWindow 的拖动事件
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        // 获取触摸点相对于屏幕的坐标值
        float rawX = event.getRawX();
        float rawY = event.getRawY();

        // 兼容处理多点触控的情况
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                // 处理横竖屏切换后没有重新获取屏幕参数
                mScreenContentWidth = DisplayUtil.getScreenContentWidth(mActivity);
                mScreenContentHeight = DisplayUtil.getScreenContentHeight(mActivity);
                mScreenHardHeight = DisplayUtil.getScreenHardwareHeight(mActivity);

                // 按下时恢复到正常显示状态，恢复其存在感
                restoreTheSenseOfPresence();

                // 记录最近一次触摸点的坐标
                mLastTouchPointX = rawX;
                mLastTouchPointY = rawY;

                break;
            case MotionEvent.ACTION_MOVE:

                // 计算拖动距离（当前触摸点的坐标 - 上一次触摸点的坐标）
                float offsetX = rawX - mLastTouchPointX;
                float offsetY = rawY - mLastTouchPointY;

                // 计算前后两次触摸点的距离
                int distance = (int) Math.sqrt(offsetX * offsetX + offsetY * offsetY);

                // 点击事件的偏移修正，消除"点击抖动"
                if (distance >= OFFSET_ALLOW_DISTANCE) {

                    // 对于超过偏移允许范围的情况，认定为拖动事件
                    isDragAction = true;

                    int parentWidth = mScreenContentWidth;
                    int parentHeight = mScreenContentHeight;

                    // 检测是否到达父view边缘 左上右下（顶部以状态栏为界）
                    float x = rawX < 0 ? 0 : rawX > parentWidth - mContentView.getWidth() ? parentWidth - mContentView.getWidth() : rawX + offsetX;
                    float y = rawY < 0 ? 0 : rawY + mContentView.getHeight() > parentHeight ? parentHeight - mContentView.getHeight() : rawY + offsetY;

                    // 更新 PopupWindow 位置
                    updatePopupWindowPosition((int) x, (int) y);

                    // 更新最近一次触摸点的坐标
                    mLastTouchPointX = rawX;
                    mLastTouchPointY = rawY;
                }
                break;
            case MotionEvent.ACTION_UP:

                // 如果是拖动行为，
                if (isDragAction) {

                    // 恢复到未按压效果
                    mContentView.setPressed(false);
                    // 恢复拖动标记状态
                    isDragAction = false;

                    final int parentWidth = mScreenContentWidth;
                    int parentHeight = mScreenContentHeight;

                    // 距离 top 边线的距离
                    float topDistance = mCurrentPopupWindowPositionY;
                    // 距离 bottom 边线的距离
                    float bottomDistance = parentHeight - mCurrentPopupWindowPositionY - mContentView.getHeight();
                    // 距离 left 边线的距离
                    float leftDistance = mCurrentPopupWindowPositionX;
                    // 距离 right 边线的距离
                    float rightDistance = parentWidth - mCurrentPopupWindowPositionX - mContentView.getWidth();

                    // 找到x轴上离边线的最近距离
                    float xMinDistance = leftDistance <= rightDistance ? leftDistance : rightDistance;
                    // 找到y轴上离边线的最近距离
                    float yMinDistance = topDistance <= bottomDistance ? topDistance : bottomDistance;

                    // 处理全屏应用的底部边界计算问题
                    final int bottomY;
                    if (parentHeight == mScreenHardHeight) {
                        bottomY = parentHeight - mContentView.getHeight();
                    } else {
                        bottomY = parentHeight - mContentView.getHeight() - mStatusHeight;
                    }

                    // 拖动完成时的自动贴靠屏幕边缘的动画
                    ValueAnimator moveToEdgeAnimator;

                    // 根据距离判断移动方向
                    if (xMinDistance <= yMinDistance) {
                        // 横向拖动超过父 View 一半时，贴靠到屏幕右侧
                        if (mCurrentPopupWindowPositionX > parentWidth / 2) {
                            currentEdge = Edge.RIGHT;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(leftDistance, parentWidth - mContentView.getWidth());
                        } else {
                            // 否则贴靠屏幕左侧
                            currentEdge = Edge.LEFT;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(leftDistance, 0);
                        }

                        moveToEdgeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentX = (float) animation.getAnimatedValue();
                                // 更新 PopupWindow 位置，参数是相对屏幕左上角为原点的偏移量
                                updatePopupWindowPosition((int) currentX, mCurrentPopupWindowPositionY);
                            }
                        });

                    } else {
                        // 竖向拖动超过父View一半时，贴靠到屏幕下侧
                        if (mCurrentPopupWindowPositionY > parentHeight / 2) {
                            currentEdge = Edge.BOTTOM;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(topDistance, bottomY);
                        } else {
                            // 否则贴靠屏幕上侧
                            currentEdge = Edge.TOP;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(topDistance, 0);
                        }

                        moveToEdgeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentY = (float) animation.getAnimatedValue();
                                // 更新 PopupWindow 位置，参数是相对屏幕左上角为原点的偏移量
                                updatePopupWindowPosition(mCurrentPopupWindowPositionX, (int) currentY);
                            }
                        });
                    }

                    // 自动贴靠屏幕边缘动画完成时，降低其存在感
                    moveToEdgeAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            // 动画完成时自动降低其存在感
                            reduceTheSenseOfPresence();
                        }
                    });

                    // 设置动画插值器
                    moveToEdgeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    moveToEdgeAnimator.setDuration(600);
                    moveToEdgeAnimator.start();
                } else {
                    // 点击完成后自动降低其存在感
                    reduceTheSenseOfPresence();
                }
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 通过设置 margin 来改变内容布局的位置
     */
    private void updateContentLayoutParams(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mContentView.getWidth(), mContentView.getHeight());
        layoutParams.setMargins(left, top, right, bottom);
        mContentView.setLayoutParams(layoutParams);
    }

    /**
     * 更新 PopupWindow 相对于屏幕的位置
     *
     * @param x 相对于屏幕左上角为原点的偏移量
     * @param y 相对于屏幕左上角为原点的偏移量
     */
    private void updatePopupWindowPosition(int x, int y) {

        mCurrentPopupWindowPositionX = x;
        mCurrentPopupWindowPositionY = y;

        // 相对位置更新，是相对于当前的位置进行偏移，注意 showAtLocation 设置的不同的显示位置时其对应的相对原点也不同，
        // 这个方法的相对原点是 showAtLocation 时自身位置，并且不会随着其位置改变而改变
        mCurrentPopupWindow.update(x, y, -1, -1);
    }

    /**
     * Builder 模式，构建 FloatDragPopupWindow
     */
    public static class Builder {

        private Activity activity;
        private View contentView;
        private int windowWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int windowHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        private Drawable backgroundDrawable = new ColorDrawable(Color.TRANSPARENT);
        private View.OnClickListener onClickListener;
        private int x = 0;
        private int y = 300;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * 设置窗户的展示内容布局
         *
         * @param contentView 内容布局
         * @return Builder
         */
        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        /**
         * 设置窗口大小，默认是 WRAP_CONTENT
         *
         * @param width  窗口宽，默认是 WRAP_CONTENT
         * @param height 窗口高，默认是 WRAP_CONTENT
         * @return Builder
         */
        public Builder setWindowSize(int width, int height) {
            this.windowWidth = width;
            this.windowHeight = height;
            return this;
        }

        /**
         * 设置窗口背景，默认透明
         *
         * @param background 窗口背景，默认透明
         * @return Builder
         */
        public Builder setBackgroundDrawable(Drawable background) {
            this.backgroundDrawable = background;
            return this;
        }

        /**
         * 设置窗口点击监听器
         *
         * @param onClickListener onClickListener
         * @return Builder
         */
        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        /**
         * 设置窗口初始位置
         *
         * @param x 相对与屏幕左上角为原点的 x 轴偏移量
         * @param y 相对与屏幕左上角为原点的 y 轴偏移量，默认 300
         * @return Builder
         */
        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        /**
         * 构建一个 FloatDragPopupWindow
         *
         * @return FloatDragPopupWindow
         */
        public FloatDragPopupWindow build() {
            return new FloatDragPopupWindow(this);
        }
    }
}
