package vn.loitp.app.activity.customviews.layout.constraintlayout.constraintset;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.core.base.BaseFontActivity;

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;

public class ConstraintSetActivity extends BaseFontActivity {
    private static final String SHOW_BIG_IMAGE = "showBigImage";
    private boolean mShowBigImage = false;
    private ConstraintLayout mRootLayout;
    private ConstraintSet mConstraintSetNormal = new ConstraintSet();
    private ConstraintSet mConstraintSetBig = new ConstraintSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootLayout = findViewById(R.id.activity_constraintset_example);
        mConstraintSetNormal.clone(mRootLayout);
        mConstraintSetBig.load(this, R.layout.constraintset_example_big);
        if (savedInstanceState != null) {
            boolean previous = savedInstanceState.getBoolean(SHOW_BIG_IMAGE);
            if (previous != mShowBigImage) {
                mShowBigImage = previous;
                applyConfig();
            }
        }

        findViewById(R.id.imageView).setOnClickListener(this::toggleMode);
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
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SHOW_BIG_IMAGE, mShowBigImage);
    }

    public void toggleMode(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(mRootLayout);
        }
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
