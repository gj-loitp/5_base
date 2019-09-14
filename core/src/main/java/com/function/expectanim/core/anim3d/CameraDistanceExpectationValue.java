package com.function.expectanim.core.anim3d;

import android.view.View;

import androidx.annotation.Nullable;

public class CameraDistanceExpectationValue extends CameraDistanceExpectation {

    private final float mCameraDistance;

    public CameraDistanceExpectationValue(float cameraDistance) {
        mCameraDistance = cameraDistance;
    }

    @Override
    @Nullable
    public Float getCalculatedCameraDistance(View viewToMove) {
        return mCameraDistance * viewToMove.getResources().getDisplayMetrics().density;
    }

}
