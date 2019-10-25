package vn.loitp.app.activity.customviews.layout.motionlayout.helpers;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ExampleFlyinBounceHelper extends ConstraintHelper {
    protected ConstraintLayout mContainer;

    public ExampleFlyinBounceHelper(Context context) {
        super(context);
    }

    public ExampleFlyinBounceHelper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleFlyinBounceHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updatePreLayout(ConstraintLayout container) {
        if (mContainer != container) {
            View[] views = getViews(container);
            for (int i = 0; i < mCount; i++) {
                View view = views[i];
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -2000, 0).setDuration(1000);
                animator.setInterpolator(new BounceInterpolator());
                animator.start();
            }
        }
        mContainer = container;
    }
}
