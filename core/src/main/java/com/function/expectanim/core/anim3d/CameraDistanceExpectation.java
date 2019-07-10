package com.function.expectanim.core.anim3d;

import android.view.View;

import androidx.annotation.Nullable;

import com.function.expectanim.core.AnimExpectation;

/**
 * Created by Christian Ringshofer on 17/02/2017.
 */
public abstract class CameraDistanceExpectation extends AnimExpectation {

    @Nullable
    public abstract Float getCalculatedCameraDistance(View viewToMove);

}
