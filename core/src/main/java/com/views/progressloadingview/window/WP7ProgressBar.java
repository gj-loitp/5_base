package com.views.progressloadingview.window;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.R;
import com.core.utilities.LAppResource;

import java.util.ArrayList;
//TODO convert kotlin
public class WP7ProgressBar extends LinearLayout {

    private static final int INTERVAL_DEF = 150;
    private static final int INDICATOR_COUNT_DEF = 5;
    private static final int ANIMATION_DURATION_DEF = 2200;
    private static final int INDICATOR_HEIGHT_DEF = 5;
    private static final int INDICATOR_RADIUS_DEF = 0;

    private int interval;
    private int animationDuration;
    private int indicatorHeight;
    private int indicatorColor;
    private int indicatorRadius;

    private boolean isShowing = false;
    private ArrayList<WP7Indicator> WP7Indicators;

    private Handler handler;
    private int progressBarCount = 0;

    private ObjectAnimator objectAnimator;

    public WP7ProgressBar(Context context) {
        super(context);
        initialize(null);
    }

    public WP7ProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public WP7ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(@Nullable AttributeSet attrs) {
        this.setGravity(Gravity.CENTER);
        this.setOrientation(HORIZONTAL);
        this.handler = new Handler();
        setAttributes(attrs);
        initializeIndicators();
    }

    private void setAttributes(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.WP7ProgressBar);
        interval = typedArray.getInt(R.styleable.WP7ProgressBar_wpInterval, INTERVAL_DEF);
        animationDuration = typedArray.getInt(R.styleable.WP7ProgressBar_wpAnimationDuration, ANIMATION_DURATION_DEF);
        indicatorHeight = typedArray.getInt(R.styleable.WP7ProgressBar_wpIndicatorHeight, INDICATOR_HEIGHT_DEF);
        indicatorRadius = typedArray.getInt(R.styleable.WP7ProgressBar_wpIndicatorRadius, INDICATOR_RADIUS_DEF);
        indicatorColor = typedArray.getColor(R.styleable.WP7ProgressBar_wpIndicatorColor, LAppResource.INSTANCE.getColor(R.color.colorAccent));
        typedArray.recycle();
    }

    private void showAnimation() {
        for (int i = 0; i < WP7Indicators.size(); i++) {
            WP7Indicators.get(i).startAnim(animationDuration, (5 - i) * interval);
        }
    }

    private void initializeIndicators() {
        this.removeAllViews();
        ArrayList<WP7Indicator> WP7Indicators = new ArrayList<>();
        for (int i = 0; i < INDICATOR_COUNT_DEF; i++) {
            WP7Indicator WP7Indicator = new WP7Indicator(getContext(), indicatorHeight, indicatorColor, indicatorRadius);
            WP7Indicators.add(WP7Indicator);
            this.addView(WP7Indicator);
        }
        this.WP7Indicators = WP7Indicators;
    }

    private void show() {
        if (isShowing)
            return;
        isShowing = true;
        showAnimation();
//        startWholeViewAnimation();
    }

    private void hide() {
        clearIndicatorsAnimations();
        isShowing = false;
//        hideWholeViewAnimation();
    }

    private void startWholeViewAnimation() {
        objectAnimator = ObjectAnimator.ofFloat(this, "translationX", -200, 200);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(animationDuration + (5 * interval));
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    private void hideWholeViewAnimation() {
        objectAnimator.removeAllListeners();
        objectAnimator.cancel();
        objectAnimator.end();
    }

    private void clearIndicatorsAnimations() {
        for (WP7Indicator WP7Indicator : WP7Indicators) {
            WP7Indicator.removeAnim();
        }
    }

    public void showProgressBar() {
        progressBarCount++;
        if (progressBarCount == 1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    WP7ProgressBar.this.show();
                }
            });
        }
    }

    public void hideProgressBar() {
        progressBarCount--;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressBarCount == 0) {
                    WP7ProgressBar.this.hide();
                }
            }
        }, 50);
    }

    public void setInterval(int interval) {
        this.interval = interval;
        initializeIndicators();
    }

    public void setAnimationDuration(int animationDuration) {
        this.animationDuration = animationDuration;
        initializeIndicators();
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        initializeIndicators();
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        initializeIndicators();
    }

    public void setIndicatorRadius(int indicatorRadius) {
        this.indicatorRadius = indicatorRadius;
        initializeIndicators();
    }
}
