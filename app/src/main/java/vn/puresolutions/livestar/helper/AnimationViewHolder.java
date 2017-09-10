package vn.puresolutions.livestar.helper;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;


public class AnimationViewHolder {
    private View animationView;
    private Animation animation;
    private RelativeLayout.LayoutParams params;

    public AnimationViewHolder(View animationView, int animationId, RelativeLayout.LayoutParams params) {
        this.animationView = animationView;
        this.params = params;
        this.animation = AnimationUtils.loadAnimation(animationView.getContext(), animationId);
    }

    public void setListener(Animation.AnimationListener listener) {
        this.animation.setAnimationListener(listener);
    }

    public View getAnimationView() {
        return animationView;
    }

    public void setAnimationView(View animationView) {
        this.animationView = animationView;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void start() {
        animationView.startAnimation(animation);
    }

    public RelativeLayout.LayoutParams getParams() {
        return params;
    }

    public void setParams(RelativeLayout.LayoutParams params) {
        this.params = params;
    }
}
