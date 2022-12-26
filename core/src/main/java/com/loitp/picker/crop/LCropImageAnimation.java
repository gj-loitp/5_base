package com.loitp.picker.crop;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

/**
 * Animation to handle smooth cropping image matrix transformation change, specifically for zoom-in/out.
 */

final class LCropImageAnimation extends Animation implements Animation.AnimationListener {

    //region: Fields and Consts

    private final ImageView mImageView;

    private final LCropOverlayView mLCropOverlayView;

    private final float[] mStartBoundPoints = new float[8];

    private final float[] mEndBoundPoints = new float[8];

    private final RectF mStartCropWindowRect = new RectF();

    private final RectF mEndCropWindowRect = new RectF();

    private final float[] mStartImageMatrix = new float[9];

    private final float[] mEndImageMatrix = new float[9];

    private final RectF mAnimRect = new RectF();

    private final float[] mAnimPoints = new float[8];

    private final float[] mAnimMatrix = new float[9];
    //endregion

    public LCropImageAnimation(ImageView cropImageView, LCropOverlayView LCropOverlayView) {
        mImageView = cropImageView;
        mLCropOverlayView = LCropOverlayView;

        setDuration(300);
        setFillAfter(true);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);
    }

    public void setStartState(float[] boundPoints, Matrix imageMatrix) {
        reset();
        System.arraycopy(boundPoints, 0, mStartBoundPoints, 0, 8);
        mStartCropWindowRect.set(mLCropOverlayView.getCropWindowRect());
        imageMatrix.getValues(mStartImageMatrix);
    }

    public void setEndState(float[] boundPoints, Matrix imageMatrix) {
        System.arraycopy(boundPoints, 0, mEndBoundPoints, 0, 8);
        mEndCropWindowRect.set(mLCropOverlayView.getCropWindowRect());
        imageMatrix.getValues(mEndImageMatrix);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        mAnimRect.left = mStartCropWindowRect.left + (mEndCropWindowRect.left - mStartCropWindowRect.left) * interpolatedTime;
        mAnimRect.top = mStartCropWindowRect.top + (mEndCropWindowRect.top - mStartCropWindowRect.top) * interpolatedTime;
        mAnimRect.right = mStartCropWindowRect.right + (mEndCropWindowRect.right - mStartCropWindowRect.right) * interpolatedTime;
        mAnimRect.bottom = mStartCropWindowRect.bottom + (mEndCropWindowRect.bottom - mStartCropWindowRect.bottom) * interpolatedTime;
        mLCropOverlayView.setCropWindowRect(mAnimRect);

        for (int i = 0; i < mAnimPoints.length; i++) {
            mAnimPoints[i] = mStartBoundPoints[i] + (mEndBoundPoints[i] - mStartBoundPoints[i]) * interpolatedTime;
        }
        mLCropOverlayView.setBounds(mAnimPoints, mImageView.getWidth(), mImageView.getHeight());

        for (int i = 0; i < mAnimMatrix.length; i++) {
            mAnimMatrix[i] = mStartImageMatrix[i] + (mEndImageMatrix[i] - mStartImageMatrix[i]) * interpolatedTime;
        }
        final Matrix m = mImageView.getImageMatrix();
        m.setValues(mAnimMatrix);
        mImageView.setImageMatrix(m);

        mImageView.invalidate();
        mLCropOverlayView.invalidate();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        mImageView.clearAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
