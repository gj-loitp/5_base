package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LScreenUtil;

//http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
public class CoordinatorLayoutSampleActivity extends BaseFontActivity {
    public static final String KEY = "KEY";
    public static final String VALUE_0 = "Standard App bar scrolling with only Toolbar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = getIntent().getStringExtra(KEY);
        switch (value) {
            case VALUE_0:
                LScreenUtil.addFragment(activity, R.id.fl_container, new FrmCoordinator0(), false);
                break;
        }
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
        return R.layout.activity_coordinator_layout_sample;
    }

}
