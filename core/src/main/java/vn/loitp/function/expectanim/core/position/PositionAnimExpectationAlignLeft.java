package vn.loitp.function.expectanim.core.position;

import android.view.View;

/**
 * Created by florentchampigny on 17/02/2017.
 */

public class PositionAnimExpectationAlignLeft extends PositionAnimationViewDependant {

    public PositionAnimExpectationAlignLeft(View otherView) {
        super(otherView);

        setForPositionX(true);
    }

    @Override
    public Float getCalculatedValueX(View viewToMove) {
        return viewCalculator.finalPositionLeftOfView(otherView) + getMargin(viewToMove) ;
    }

    @Override
    public Float getCalculatedValueY(View viewToMove) {
        return null;
    }
}
