package vn.loitp.views.draggableflipview;

import android.animation.ValueAnimator;
import android.view.View;

/**
 * this is animation class for rotate
 *
 * Created by sasakicks on 2015/09/09.
 */
public class FlipListener implements ValueAnimator.AnimatorUpdateListener {

    private View mParentView;
    private View mFrontView;
    private View mBackView;
    private boolean mFlipped;
    private int mDirection;

    public FlipListener(final View front, final View back, final View parent) {
        mParentView = parent;
        mFrontView = front;
        mBackView = back;
        mBackView.setVisibility(View.GONE);
    }

    @Override
    public void onAnimationUpdate(final ValueAnimator animation) {
        final float value = animation.getAnimatedFraction();
        final float scaleValue = 0.625f + (1.5f * (value - 0.5f) * (value - 0.5f));

        if (value <= 0.5f) {
            mParentView.setRotationY(180 * value * mDirection);
            if (mFlipped) setStateFlipped(false);
        } else {
            mParentView.setRotationY(-180 * (1 - value) * mDirection);
            if (!mFlipped) setStateFlipped(true);
        }
        mParentView.setScaleX(scaleValue);
        mParentView.setScaleY(scaleValue);
    }

    public void reverse() {
        View temp = mBackView;
        mBackView = mFrontView;
        mFrontView = temp;
    }

    public void setRotateDirection(int direction) {
        mDirection = direction;
    }

    private void setStateFlipped(boolean flipped) {
        mFlipped = flipped;
        mFrontView.setVisibility(flipped ? View.GONE : View.VISIBLE);
        mBackView.setVisibility(flipped ? View.VISIBLE : View.GONE);
    }
}

