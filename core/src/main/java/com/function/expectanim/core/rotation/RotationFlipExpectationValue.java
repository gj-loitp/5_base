package com.function.expectanim.core.rotation;

import android.view.View;

import androidx.annotation.Nullable;


public class RotationFlipExpectationValue extends RotationExpectation {

    private final float mRotationX, mRotationY;

    public RotationFlipExpectationValue(float rotationX, float rotationY) {
        mRotationX = rotationX;
        mRotationY = rotationY;
    }

    @Override
    public Float getCalculatedRotation(View viewToMove) {
        return null;
    }

    @Override
    @Nullable
    public Float getCalculatedRotationX(View viewToMove) {
        return mRotationX;
    }

    @Override
    @Nullable
    public Float getCalculatedRotationY(View viewToMove) {
        return mRotationY;
    }

}
