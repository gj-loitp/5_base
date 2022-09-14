package com.loitpcore.views.layout.floatDrag;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import timber.log.Timber;

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
public class FloatDragLayout extends FrameLayout {

    public static final String TAG = "FloatDragLayout";

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
     * 屏幕物理尺寸像素高度
     */
    private int mScreenHardHeight;

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


    private void init(Context context) {
        mScreenContentWidth = DisplayUtil.getScreenContentWidth(context);
        mScreenContentHeight = DisplayUtil.getScreenContentHeight(context);
        mStatusHeight = DisplayUtil.getStatusHeight(context);

        int mNavigationBarHeight = DisplayUtil.getNavigationBarHeight(context);
        mScreenHardHeight = DisplayUtil.getScreenHardwareHeight(context);
        int mScreenHardWidth = DisplayUtil.getScreenHardwareWidth(context);
        Timber.tag(TAG).d("FloatDragLayout init mScreenContentWidth=" + mScreenContentWidth + ", mScreenContentHeight=" + mScreenContentHeight + ", mScreenHardWidth=" + mScreenHardWidth + ",mScreenHardHeight=" + mScreenHardHeight + ",StatusHeight=" + mStatusHeight + ",mNavigationBarHeight" + mNavigationBarHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float rawX = event.getRawX();
        float rawY = event.getRawY();

        mScreenContentWidth = DisplayUtil.getScreenContentWidth(getContext());
        mScreenContentHeight = DisplayUtil.getScreenContentHeight(getContext());


        int OFFSET_ALLOW_DISTANCE = 10;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);

                mLastTouchPointX = rawX;
                mLastTouchPointY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = rawX - mLastTouchPointX;
                float distanceY = rawY - mLastTouchPointY;

                int distance = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                if (distance >= OFFSET_ALLOW_DISTANCE) {

                    isDragAction = true;

                    View parent = (View) getParent();
                    int parentHeight = parent.getHeight();
                    int parentWidth = parent.getWidth();

                    if (parentHeight + mStatusHeight >= mScreenContentHeight) {
                        parentHeight = mScreenContentHeight;
                    }

                    float x = getX() + distanceX;
                    float y = getY() + distanceY;

                    distanceX = x < 0 ? 0 : x > parentWidth - getWidth() ? 0 : distanceX;
                    distanceY = y < 0 ? 0 : y + getHeight() > parentHeight ? 0 : distanceY;

                    ViewCompat.offsetLeftAndRight(this, (int) distanceX);
                    ViewCompat.offsetTopAndBottom(this, (int) distanceY);

                    mLastTouchPointX = rawX;
                    mLastTouchPointY = rawY;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDragAction) {
                    setPressed(false);
                    isDragAction = false;

                    updateLayoutParams(getLeft(), getTop(), getRight(), getBottom());

                    if (isNearScreenEdge) {

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

                        float topDistance = getY();
                        float bottomDistance = parentHeight - getY() - getHeight();
                        float leftDistance = getX();
                        float rightDistance = parentWidth - getX() - getWidth();

                        float xMinDistance = Math.min(leftDistance, rightDistance);
                        float yMinDistance = Math.min(topDistance, bottomDistance);

                        ObjectAnimator moveToEdgeAnimator;
                        if (xMinDistance <= yMinDistance) {
                            if (getX() > parentWidth / 2) {
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "x", leftDistance, parentWidth - getWidth());
                            } else {
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "x", leftDistance, 0);
                            }
                        } else {
                            if (getY() > parentHeight / 2) {
                                moveToEdgeAnimator = ObjectAnimator.ofFloat(this, "y", topDistance, bottomY);
                            } else {
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
        super.onTouchEvent(event);
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

    @Override
    protected Parcelable onSaveInstanceState() {
        FloatDragLayout.SavedViewState state = new FloatDragLayout.SavedViewState(super.onSaveInstanceState());
        state.lastTouchPointX = mLastTouchPointX;
        state.lastTouchPointY = mLastTouchPointY;
        state.isDragAction = isDragAction;
        return state;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
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
