package vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.core.utilities.LLog;
import com.views.imageview.circle.LCircleImageView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Obaro on 18/07/2016.
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<LCircleImageView> {

    private final String TAG = getClass().getSimpleName();

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NotNull CoordinatorLayout parent, @NotNull LCircleImageView child, @NotNull View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(@NotNull CoordinatorLayout parent, LCircleImageView child, View dependency) {
        int[] dependencyLocation = new int[2];
        int[] childLocation = new int[2];

        dependency.getLocationInWindow(dependencyLocation);
        child.getLocationInWindow(childLocation);

        float diff = childLocation[1] - dependencyLocation[1];
        if (diff > 0) {
            float scale = diff / (float) childLocation[1];
            LLog.d(TAG, "scale == " + scale);
            child.setScaleX(1 + scale);
            child.setScaleY(1 + scale);
        }
        return false;
    }
}
