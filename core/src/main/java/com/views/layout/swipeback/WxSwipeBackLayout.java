package com.views.layout.swipeback;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.views.layout.swipeback.tools.Util;
//TODO convert kotlin
public class WxSwipeBackLayout extends SwipeBackLayout {
    private static final String TAG = "WxSwipeBackLayout";

    public WxSwipeBackLayout(@NonNull Context context) {
        this(context, null);
    }

    public WxSwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WxSwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSwipeBackListener(defaultSwipeBackListener);
    }

    @Override
    public void setDirectionMode(int direction) {
        super.setDirectionMode(direction);
        if (direction != SwipeBackLayout.FROM_LEFT)
            throw new IllegalArgumentException("The direction of WxSwipeBackLayout must be FROM_LEFT");
    }

    private OnSwipeBackListener defaultSwipeBackListener = new OnSwipeBackListener() {
        @Override
        public void onViewPositionChanged(View mView, float swipeBackFraction, float swipeBackFactor) {
            invalidate();
            Util.onPanelSlide(swipeBackFraction);
        }

        @Override
        public void onViewSwipeFinished(View mView, boolean isEnd) {
            if (isEnd) {
                finish();
            }
            Util.onPanelReset();
        }
    };
}
