package vn.loitp.function.expectanim.core.position;

import android.view.View;

import java.util.List;

import vn.loitp.function.expectanim.ViewCalculator;

/**
 * Created by florentchampigny on 17/02/2017.
 */

public abstract class PositionAnimationViewDependant extends PositionAnimExpectation {

    protected View otherView;

    public PositionAnimationViewDependant(View otherView) {
        this.otherView = otherView;
    }

    @Override
    public List<View> getViewsDependencies() {
        final List<View> viewsDependencies = super.getViewsDependencies();
        viewsDependencies.add(otherView);
        return viewsDependencies;
    }

    public void setViewCalculator(ViewCalculator viewCalculator) {
        this.viewCalculator = viewCalculator;
    }

}
