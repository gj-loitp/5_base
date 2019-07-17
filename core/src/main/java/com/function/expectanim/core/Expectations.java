package com.function.expectanim.core;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.IntDef;

import com.function.expectanim.core.alpha.AlphaAnimExpectation;
import com.function.expectanim.core.alpha.AlphaAnimExpectationValue;
import com.function.expectanim.core.anim3d.CameraDistanceExpectation;
import com.function.expectanim.core.anim3d.CameraDistanceExpectationValue;
import com.function.expectanim.core.custom.CustomAnimExpectation;
import com.function.expectanim.core.custom.TextColorAnimExpectation;
import com.function.expectanim.core.custom.ViewBackgroundAlphaAnimExpectation;
import com.function.expectanim.core.position.PositionAnimExpectation;
import com.function.expectanim.core.position.PositionAnimExpectationAboveOf;
import com.function.expectanim.core.position.PositionAnimExpectationAlignBottom;
import com.function.expectanim.core.position.PositionAnimExpectationAlignLeft;
import com.function.expectanim.core.position.PositionAnimExpectationAlignRight;
import com.function.expectanim.core.position.PositionAnimExpectationAlignTop;
import com.function.expectanim.core.position.PositionAnimExpectationBelowOf;
import com.function.expectanim.core.position.PositionAnimExpectationBottomOfParent;
import com.function.expectanim.core.position.PositionAnimExpectationCenterBetweenViewAndParent;
import com.function.expectanim.core.position.PositionAnimExpectationCenterBetweenViews;
import com.function.expectanim.core.position.PositionAnimExpectationCenterInParent;
import com.function.expectanim.core.position.PositionAnimExpectationLeftOf;
import com.function.expectanim.core.position.PositionAnimExpectationLeftOfParent;
import com.function.expectanim.core.position.PositionAnimExpectationOriginal;
import com.function.expectanim.core.position.PositionAnimExpectationOutOfScreen;
import com.function.expectanim.core.position.PositionAnimExpectationRightOf;
import com.function.expectanim.core.position.PositionAnimExpectationRightOfParent;
import com.function.expectanim.core.position.PositionAnimExpectationSameCenterAs;
import com.function.expectanim.core.position.PositionAnimExpectationTopOfParent;
import com.function.expectanim.core.rotation.RotationExpectation;
import com.function.expectanim.core.rotation.RotationExpectationOriginal;
import com.function.expectanim.core.rotation.RotationExpectationValue;
import com.function.expectanim.core.rotation.RotationFlipExpectationValue;
import com.function.expectanim.core.scale.ScaleAnimExpectation;
import com.function.expectanim.core.scale.ScaleAnimExpectationHeight;
import com.function.expectanim.core.scale.ScaleAnimExpectationOriginalScale;
import com.function.expectanim.core.scale.ScaleAnimExpectationSameHeightAs;
import com.function.expectanim.core.scale.ScaleAnimExpectationSameScaleAs;
import com.function.expectanim.core.scale.ScaleAnimExpectationSameWidthAs;
import com.function.expectanim.core.scale.ScaleAnimExpectationValues;
import com.function.expectanim.core.scale.ScaleAnimExpectationWidth;


/**
 * Created by florentchampigny on 20/02/2017.
 */

public class Expectations {

    //region position

    public static PositionAnimExpectation toRightOf(View view) {
        return new PositionAnimExpectationRightOf(view);
    }

    public static PositionAnimExpectation toLeftOf(View view) {
        return new PositionAnimExpectationLeftOf(view);
    }

    public static PositionAnimExpectation belowOf(View view) {
        return new PositionAnimExpectationBelowOf(view);
    }

    public static PositionAnimExpectation aboveOf(View view) {
        return new PositionAnimExpectationAboveOf(view);
    }

    public static PositionAnimExpectation atItsOriginalPosition() {
        return new PositionAnimExpectationOriginal();
    }

    public static PositionAnimExpectation sameCenterAs(View view, boolean horizontal, boolean vertical) {
        return new PositionAnimExpectationSameCenterAs(view, horizontal, vertical);
    }

    public static PositionAnimExpectation sameCenterHorizontalAs(View view) {
        return sameCenterAs(view, true, false);
    }

    public static PositionAnimExpectation sameCenterVerticalAs(View view) {
        return sameCenterAs(view, false, true);
    }

    public static PositionAnimExpectation centerInParent(boolean horizontal, boolean vertical) {
        return new PositionAnimExpectationCenterInParent(horizontal, vertical);
    }

    public static PositionAnimExpectation centerVerticalInParent() {
        return centerInParent(false, true);
    }

    public static PositionAnimExpectation centerHorizontalInParent() {
        return centerInParent(true, false);
    }

    public static PositionAnimExpectation outOfScreen(@GravityIntDef int... gravities) {
        return new PositionAnimExpectationOutOfScreen(gravities);
    }

    public static PositionAnimExpectation centerBetweenViews(View view1, View view2, boolean horizontal, boolean vertical) {
        return new PositionAnimExpectationCenterBetweenViews(view1, view2, horizontal, vertical);
    }

    public static PositionAnimExpectation topOfParent() {
        return new PositionAnimExpectationTopOfParent();
    }

    public static PositionAnimExpectation rightOfParent() {
        return new PositionAnimExpectationRightOfParent();
    }

    public static PositionAnimExpectation bottomOfParent() {
        return new PositionAnimExpectationBottomOfParent();
    }

    public static PositionAnimExpectation leftOfParent() {
        return new PositionAnimExpectationLeftOfParent();
    }

    public static PositionAnimExpectation centerBetweenViewAndParent(View otherView, boolean horizontal, boolean vertical, boolean toBeOnRight, boolean toBeOnBottom) {
        return new PositionAnimExpectationCenterBetweenViewAndParent(otherView, horizontal, vertical, toBeOnRight, toBeOnBottom);
    }

    public static PositionAnimExpectation alignBottom(View otherView) {
        return new PositionAnimExpectationAlignBottom(otherView);
    }

    public static PositionAnimExpectation alignTop(View otherView) {
        return new PositionAnimExpectationAlignTop(otherView);
    }

    public static PositionAnimExpectation alignLeft(View otherView) {
        return new PositionAnimExpectationAlignLeft(otherView);
    }

    public static PositionAnimExpectation alignRight(View otherView) {
        return new PositionAnimExpectationAlignRight(otherView);
    }

    @IntDef(value = {Gravity.LEFT, Gravity.RIGHT, Gravity.END, Gravity.START, Gravity.TOP, Gravity.BOTTOM})
    @interface GravityIntDef {
    }

    //endregion

    //region alpha

    public static AlphaAnimExpectation alpha(float alpha) {
        return new AlphaAnimExpectationValue(alpha);
    }

    public static AlphaAnimExpectation sameAlphaAs(View otherView) {
        return new AlphaAnimExpectationValue(otherView.getAlpha());
    }

    public static AlphaAnimExpectation visible() {
        return new AlphaAnimExpectationValue(1f);
    }

    public static AlphaAnimExpectation invisible() {
        return new AlphaAnimExpectationValue(0f);
    }

    //endregion

    //region scale

    @IntDef(value = {Gravity.TOP, Gravity.BOTTOM, Gravity.CENTER, Gravity.CENTER_VERTICAL})
    public @interface GravityScaleVerticalIntDef {
    }

    @IntDef(value = {Gravity.LEFT, Gravity.RIGHT, Gravity.END, Gravity.START, Gravity.CENTER, Gravity.CENTER_HORIZONTAL})
    public @interface GravityScaleHorizontalIntDef {
    }

    public static ScaleAnimExpectation atItsOriginalScale() {
        return new ScaleAnimExpectationOriginalScale();
    }

    public static ScaleAnimExpectation scale(float scaleX, float scaleY, @GravityScaleHorizontalIntDef int gravityHorizontal, @GravityScaleVerticalIntDef int gravityVertical) {
        return new ScaleAnimExpectationValues(scaleX, scaleY, gravityHorizontal, gravityVertical);
    }

    public static ScaleAnimExpectation scale(float scaleX, float scaleY) {
        return new ScaleAnimExpectationValues(scaleX, scaleY, null, null);
    }

    public static ScaleAnimExpectation height(int height, @GravityScaleHorizontalIntDef int gravityHorizontal, @GravityScaleVerticalIntDef int gravityVertical) {
        return new ScaleAnimExpectationHeight(height, gravityHorizontal, gravityVertical);
    }

    public static ScaleAnimExpectation height(int height) {
        return new ScaleAnimExpectationHeight(height, null, null);
    }

    public static ScaleAnimExpectation width(int width, @GravityScaleHorizontalIntDef int gravityHorizontal, @GravityScaleVerticalIntDef int gravityVertical) {
        return new ScaleAnimExpectationWidth(width, gravityHorizontal, gravityVertical);
    }

    public static ScaleAnimExpectation width(int width) {
        return new ScaleAnimExpectationWidth(width, null, null);
    }

    public static ScaleAnimExpectation sameScaleAs(View otherView) {
        return new ScaleAnimExpectationSameScaleAs(otherView);
    }

    public static ScaleAnimExpectation sameWidthAs(View otherView) {
        return new ScaleAnimExpectationSameWidthAs(otherView, null, null);
    }

    public static ScaleAnimExpectation sameHeightAs(View otherView) {
        return new ScaleAnimExpectationSameHeightAs(otherView, null, null);
    }

    //endregion

    //region custom

    public static CustomAnimExpectation toHaveTextColor(int textColor) {
        return new TextColorAnimExpectation(textColor);
    }

    public static CustomAnimExpectation toHaveBackgroundAlpha(float alpha) {
        return new ViewBackgroundAlphaAnimExpectation(alpha);
    }


    //endregion

    //region rotation

    public static RotationExpectation rotated(float rotation) {
        return new RotationExpectationValue(rotation);
    }

    public static CameraDistanceExpectation withCameraDistance(float cameraDistance) {
        return new CameraDistanceExpectationValue(cameraDistance);
    }

    public static RotationExpectation flippedHorizontally() {
        return new RotationFlipExpectationValue(0f, 180f);
    }

    public static RotationExpectation flippedVertically() {
        return new RotationFlipExpectationValue(180f, 0f);
    }

    public static RotationExpectation flippedHorizontallyAndVertically() {
        return new RotationFlipExpectationValue(180f, 180f);
    }

    public static RotationExpectation vertical(boolean bottomOfViewAtLeft) {
        if (bottomOfViewAtLeft) {
            return new RotationExpectationValue(90);
        } else {
            return new RotationExpectationValue(270);
        }
    }

    public static RotationExpectation atItsOriginalRotation() {
        return new RotationExpectationOriginal();
    }

    //endregion

}
