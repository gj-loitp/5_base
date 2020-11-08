package com.views.progressloadingview.window;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.LinearLayout;
//TODO convert kotlin
class WP7Indicator extends View {

    private ObjectAnimator objectAnimator;
    private int color;

    public WP7Indicator(Context context, int indicatorHeight, int color, int radius) {
        super(context);
        this.color = color;
        initialize(indicatorHeight, radius);
    }

    private void initialize(int indicatorHeight, int radius) {
        this.setBackground(getCube(radius));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Utils.px2dp(getContext(), indicatorHeight), Utils.px2dp(getContext(), indicatorHeight));
        layoutParams.rightMargin = Utils.px2dp(getContext(), (int) (1.5f * indicatorHeight));
        this.setLayoutParams(layoutParams);
        startAnim(0, 0);
        removeAnim();
    }

    private GradientDrawable getCube(int radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(Utils.px2dp(getContext(), radius));
        return drawable;
    }

    public void startAnim(long animationDuration, long delay) {
        objectAnimator = ObjectAnimator.ofFloat(this, "translationX", +1000, -1000);
        objectAnimator.setInterpolator(new WPInterpolator());
        objectAnimator.setDuration(animationDuration);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setStartDelay(delay);
        objectAnimator.start();
    }

    public void removeAnim() {
        objectAnimator.removeAllListeners();
        objectAnimator.cancel();
        objectAnimator.end();
    }
}
