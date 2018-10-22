package vn.loitp.app.activity.customviews.layout.constraintlayout.constraintset;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.TransitionManager;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

public class ConstraintSetActivity extends BaseFontActivity {
    private static final String SHOW_BIG_IMAGE = "showBigImage";
    private boolean mShowBigImage = false;
    private ConstraintLayout mRootLayout;
    private ConstraintSet mConstraintSetNormal = new ConstraintSet();
    private ConstraintSet mConstraintSetBig = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootLayout = (ConstraintLayout) findViewById(R.id.activity_constraintset_example);
        // Note that this can also be achieved by calling
        // `mConstraintSetNormal.load(this, R.layout.constraintset_example_main);`
        // Since we already have an inflated ConstraintLayout in `mRootLayout`, clone() is
        // faster and considered the best practice.
        mConstraintSetNormal.clone(mRootLayout);
        // Load the constraints from the layout where ImageView is enlarged.
        mConstraintSetBig.load(this, R.layout.constraintset_example_big);

        if (savedInstanceState != null) {
            boolean previous = savedInstanceState.getBoolean(SHOW_BIG_IMAGE);
            if (previous != mShowBigImage) {
                mShowBigImage = previous;
                applyConfig();
            }
        }

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMode(view);
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_constraint_set;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOW_BIG_IMAGE, mShowBigImage);
    }

    // Method called when the ImageView within R.layout.constraintset_example_main
    // is clicked.
    public void toggleMode(View v) {
        TransitionManager.beginDelayedTransition(mRootLayout);
        mShowBigImage = !mShowBigImage;
        applyConfig();
    }

    private void applyConfig() {
        if (mShowBigImage) {
            mConstraintSetBig.applyTo(mRootLayout);
        } else {
            mConstraintSetNormal.applyTo(mRootLayout);
        }
    }
}
