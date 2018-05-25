package vn.loitp.function.expectanim.core;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vn.loitp.function.expectanim.ViewCalculator;

/**
 * Created by florentchampigny on 17/02/2017.
 */

public abstract class AnimExpectation {

    protected ViewCalculator viewCalculator;

    public void setViewCalculator(ViewCalculator viewCalculator) {
        this.viewCalculator = viewCalculator;
    }

    public List<View> getViewsDependencies(){
        return new ArrayList<>();
    }
}
