package com.function.expectanim.core.rotation;

import android.view.View;

/**
 * Created by florentchampigny on 17/02/2017.
 */
public class RotationExpectationValue extends RotationExpectation {

    private final float rotation;

    public RotationExpectationValue(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public Float getCalculatedRotation(View viewToMove) {
        return rotation;
    }

    @Override
    public Float getCalculatedRotationX(View viewToMove) {
        return null;
    }

    @Override
    public Float getCalculatedRotationY(View viewToMove) {
        return null;
    }
}
