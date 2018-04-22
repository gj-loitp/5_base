package vn.loitp.app.activity.customviews.layout.constraintlayout;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;

public class ConstraintlayoutActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraintlayout);
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
        return R.layout.activity_constraintlayout;
    }

    float dX, dY;
}
