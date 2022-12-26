package com.loitp.views.layout.floatDrag;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import kotlin.Suppress;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class FloatDragPopupWindow implements PopupWindow.OnDismissListener, View.OnTouchListener {

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

    @Edge
    private int currentEdge = Edge.LEFT;
    private static final int OFFSET_ALLOW_DISTANCE = 10;
    private int mScreenContentWidth;
    private int mScreenContentHeight;
    private int mStatusHeight;
    private int mScreenHardHeight;
    private boolean isDragAction;
    private float mLastTouchPointX;
    private float mLastTouchPointY;

    private final Activity mActivity;
    private final View mContentView;
    private final int mWindowWidth;
    private final int mWindowHeight;
    private final Drawable mBackgroundDrawable;
    private FixedPopupWindow mCurrentPopupWindow;
    private int mCurrentPopupWindowPositionX;
    private int mCurrentPopupWindowPositionY;
    private final View.OnClickListener mOnClickListener;


    private FloatDragPopupWindow(Builder builder) {
        this.mActivity = builder.activity;
        this.mContentView = builder.contentView;
        this.mWindowWidth = builder.windowWidth;
        this.mWindowHeight = builder.windowHeight;
        this.mBackgroundDrawable = builder.backgroundDrawable;
        this.mOnClickListener = builder.onClickListener;
        this.mCurrentPopupWindowPositionX = builder.x;
        this.mCurrentPopupWindowPositionY = builder.y;

        initWindow();
    }

    private void initWindow() {
        mCurrentPopupWindow = new FixedPopupWindow(mContentView, mWindowWidth, mWindowHeight);
        mCurrentPopupWindow.setFocusable(false);
        mCurrentPopupWindow.setBackgroundDrawable(mBackgroundDrawable);
        mCurrentPopupWindow.setOnDismissListener(this);
        mCurrentPopupWindow.setOutsideTouchable(false);

        mScreenContentWidth = DisplayUtil.getScreenContentWidth(mActivity);
        mScreenContentHeight = DisplayUtil.getScreenContentHeight(mActivity);
        mScreenHardHeight = DisplayUtil.getScreenHardwareHeight(mActivity);
        mStatusHeight = DisplayUtil.getStatusHeight(mActivity);
    }

    public void show() {

        if (mCurrentPopupWindow == null) {
            return;
        }

        if (!mActivity.isFinishing() && !isShowing()) {

            mActivity.getWindow().getDecorView().post(() -> {

                mCurrentPopupWindow.showAtLocation(mActivity.getWindow().getDecorView(),
                        Gravity.NO_GRAVITY,
                        0, 0);

                updatePopupWindowPosition(mCurrentPopupWindowPositionX, mCurrentPopupWindowPositionY);
                mContentView.setOnTouchListener(FloatDragPopupWindow.this);
                mContentView.setOnClickListener(mOnClickListener);
                reduceTheSenseOfPresence();
            });
        }
    }

    public void reduceTheSenseOfPresence() {

        if (mContentView == null) {
            return;
        }

        mContentView.removeCallbacks(mContentViewHideRunnable);
        mContentView.postDelayed(mContentViewHideRunnable, 1000);
    }

    private final Runnable mContentViewHideRunnable = new Runnable() {
        @Override
        public void run() {

            if (isShowing()) {
                updateContentLayoutParams(0, 0, 0, 0);

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

                updateContentLayoutParams(left, top, 0, 0);
                mContentView.setAlpha(0.5f);
            }
        }
    };

    private void restoreTheSenseOfPresence() {
        mContentView.removeCallbacks(mContentViewHideRunnable);
        updateContentLayoutParams(0, 0, 0, 0);
        mContentView.setAlpha(1.0f);
    }

    public boolean isShowing() {
        return mCurrentPopupWindow != null && mCurrentPopupWindow.isShowing();
    }

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

    public View getContentView() {
        return mContentView;
    }

    @Suppress(names = "unused")
    public int getCurrentWindowX() {
        return mCurrentPopupWindowPositionX;
    }

    @Suppress(names = "unused")
    public int getCurrentWindowY() {
        return mCurrentPopupWindowPositionY;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        float rawX = event.getRawX();
        float rawY = event.getRawY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                mScreenContentWidth = DisplayUtil.getScreenContentWidth(mActivity);
                mScreenContentHeight = DisplayUtil.getScreenContentHeight(mActivity);
                mScreenHardHeight = DisplayUtil.getScreenHardwareHeight(mActivity);

                restoreTheSenseOfPresence();

                mLastTouchPointX = rawX;
                mLastTouchPointY = rawY;

                break;
            case MotionEvent.ACTION_MOVE:

                float offsetX = rawX - mLastTouchPointX;
                float offsetY = rawY - mLastTouchPointY;

                int distance = (int) Math.sqrt(offsetX * offsetX + offsetY * offsetY);
                if (distance >= OFFSET_ALLOW_DISTANCE) {

                    isDragAction = true;

                    int parentWidth = mScreenContentWidth;
                    int parentHeight = mScreenContentHeight;

                    float x = rawX < 0 ? 0 : rawX > parentWidth - mContentView.getWidth() ? parentWidth - mContentView.getWidth() : rawX + offsetX;
                    float y = rawY < 0 ? 0 : rawY + mContentView.getHeight() > parentHeight ? parentHeight - mContentView.getHeight() : rawY + offsetY;

                    updatePopupWindowPosition((int) x, (int) y);

                    mLastTouchPointX = rawX;
                    mLastTouchPointY = rawY;
                }
                break;
            case MotionEvent.ACTION_UP:

                if (isDragAction) {

                    mContentView.setPressed(false);
                    isDragAction = false;

                    final int parentWidth = mScreenContentWidth;
                    int parentHeight = mScreenContentHeight;
                    float topDistance = mCurrentPopupWindowPositionY;
                    float bottomDistance = parentHeight - mCurrentPopupWindowPositionY - mContentView.getHeight();
                    float leftDistance = mCurrentPopupWindowPositionX;
                    float rightDistance = parentWidth - mCurrentPopupWindowPositionX - mContentView.getWidth();

                    float xMinDistance = Math.min(leftDistance, rightDistance);
                    float yMinDistance = Math.min(topDistance, bottomDistance);

                    final int bottomY;
                    if (parentHeight == mScreenHardHeight) {
                        bottomY = parentHeight - mContentView.getHeight();
                    } else {
                        bottomY = parentHeight - mContentView.getHeight() - mStatusHeight;
                    }

                    ValueAnimator moveToEdgeAnimator;

                    if (xMinDistance <= yMinDistance) {
                        if (mCurrentPopupWindowPositionX > parentWidth / 2) {
                            currentEdge = Edge.RIGHT;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(leftDistance, parentWidth - mContentView.getWidth());
                        } else {
                            currentEdge = Edge.LEFT;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(leftDistance, 0);
                        }

                        moveToEdgeAnimator.addUpdateListener(animation -> {
                            float currentX = (float) animation.getAnimatedValue();
                            updatePopupWindowPosition((int) currentX, mCurrentPopupWindowPositionY);
                        });

                    } else {
                        if (mCurrentPopupWindowPositionY > parentHeight / 2) {
                            currentEdge = Edge.BOTTOM;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(topDistance, bottomY);
                        } else {
                            currentEdge = Edge.TOP;
                            moveToEdgeAnimator = ValueAnimator.ofFloat(topDistance, 0);
                        }

                        moveToEdgeAnimator.addUpdateListener(animation -> {
                            float currentY = (float) animation.getAnimatedValue();
                            updatePopupWindowPosition(mCurrentPopupWindowPositionX, (int) currentY);
                        });
                    }

                    moveToEdgeAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            reduceTheSenseOfPresence();
                        }
                    });

                    moveToEdgeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    moveToEdgeAnimator.setDuration(600);
                    moveToEdgeAnimator.start();
                } else {
                    reduceTheSenseOfPresence();
                }
                break;
            default:
                break;
        }
        return false;
    }

    @Suppress(names = "unused")
    private void updateContentLayoutParams(int left, int top, int right, int bottom) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(mContentView.getWidth(), mContentView.getHeight());
        layoutParams.setMargins(left, top, right, bottom);
        mContentView.setLayoutParams(layoutParams);
    }

    private void updatePopupWindowPosition(int x, int y) {

        mCurrentPopupWindowPositionX = x;
        mCurrentPopupWindowPositionY = y;

        mCurrentPopupWindow.update(x, y, -1, -1);
    }

    public static class Builder {

        private final Activity activity;
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

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        @Suppress(names = "unused")
        public Builder setWindowSize(int width, int height) {
            this.windowWidth = width;
            this.windowHeight = height;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable background) {
            this.backgroundDrawable = background;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public FloatDragPopupWindow build() {
            return new FloatDragPopupWindow(this);
        }
    }
}
