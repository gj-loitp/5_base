package vn.loitp.app.activity.customviews.layout.swiperefreshlayout;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;

public class SwipeRefreshLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_ripple_layout;
    }
}
