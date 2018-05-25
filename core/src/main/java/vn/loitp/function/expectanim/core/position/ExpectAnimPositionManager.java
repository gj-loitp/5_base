package vn.loitp.function.expectanim.core.position;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.function.expectanim.ViewCalculator;
import vn.loitp.function.expectanim.core.AnimExpectation;
import vn.loitp.function.expectanim.core.ExpectAnimManager;

/**
 * Created by florentchampigny on 17/02/2017.
 */

public class ExpectAnimPositionManager extends ExpectAnimManager {

    private Float positionX = null;
    private Float positionY = null;

    private Float translationX = null;
    private Float translationY = null;

    public ExpectAnimPositionManager(List<AnimExpectation> animExpectations, View viewToMove, ViewCalculator viewCalculator) {
        super(animExpectations, viewToMove, viewCalculator);
    }


    public Float getPositionX() {
        if(translationX != null){
            return viewToMove.getX() + translationX;
        } else {
            return positionX;
        }
    }

    public Float getPositionY() {
        if(translationX != null){
            return viewToMove.getY() + translationY;
        } else {
            return positionY;
        }
    }

    public void calculate() {
        for (AnimExpectation animExpectation : animExpectations) {
            if(animExpectation instanceof PositionAnimExpectation) {
                PositionAnimExpectation expectation = (PositionAnimExpectation)animExpectation;

                    expectation.setViewCalculator(viewCalculator);

                    final Float calculatedValueX = expectation.getCalculatedValueX(viewToMove);
                    if (calculatedValueX != null) {
                        if (expectation.isForPositionX()) {
                            positionX = calculatedValueX;
                        }
                        if (expectation.isForTranslationX()) {
                            translationX = calculatedValueX;
                        }
                    }

                    final Float calculatedValueY = expectation.getCalculatedValueY(viewToMove);
                    if (calculatedValueY != null) {
                        if (expectation.isForPositionY()) {
                            positionY = calculatedValueY;
                        }
                        if (expectation.isForTranslationY()) {
                            translationY = calculatedValueY;
                        }
                    }
                }
        }
    }

    @Override
    public List<Animator> getAnimators() {
        final List<Animator> animations = new ArrayList<>();

        if (positionX != null) {
            animations.add(ObjectAnimator.ofFloat(viewToMove, View.X, viewCalculator.finalPositionLeftOfView(viewToMove, true)));
        }

        if (positionY != null) {
            animations.add(ObjectAnimator.ofFloat(viewToMove, View.Y, viewCalculator.finalPositionTopOfView(viewToMove, true)));
        }

        if (translationX != null) {
            animations.add(ObjectAnimator.ofFloat(viewToMove, View.TRANSLATION_X, translationX));
        }

        if (translationY != null) {
            animations.add(ObjectAnimator.ofFloat(viewToMove, View.TRANSLATION_Y, translationY));
        }

        return animations;
    }
}
