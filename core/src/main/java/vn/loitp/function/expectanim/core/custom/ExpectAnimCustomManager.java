package vn.loitp.function.expectanim.core.custom;

import android.animation.Animator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.function.expectanim.ViewCalculator;
import vn.loitp.function.expectanim.core.AnimExpectation;
import vn.loitp.function.expectanim.core.ExpectAnimManager;

/**
 * Created by florentchampigny on 17/02/2017.
 */

public class ExpectAnimCustomManager extends ExpectAnimManager {

    private final List<Animator> animations;

    public ExpectAnimCustomManager(List<AnimExpectation> animExpectations, View viewToMove, ViewCalculator viewCalculator) {
        super(animExpectations, viewToMove, viewCalculator);
        this.animations = new ArrayList<>();
    }

    @Override
    public void calculate() {

        for (AnimExpectation animExpectation : animExpectations) {
            if (animExpectation instanceof CustomAnimExpectation) {
                final CustomAnimExpectation expectation = (CustomAnimExpectation) animExpectation;

                expectation.setViewCalculator(viewCalculator);

                final Animator animator = expectation.getAnimator(viewToMove);
                if (animator != null) {
                    animations.add(animator);
                }
            }
        }
    }

    @Override
    public List<Animator> getAnimators() {
        return animations;
    }
}
