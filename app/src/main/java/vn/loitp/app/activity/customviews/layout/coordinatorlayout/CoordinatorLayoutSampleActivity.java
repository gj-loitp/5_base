package vn.loitp.app.activity.customviews.layout.coordinatorlayout;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LScreenUtil;

import loitp.basemaster.R;

//http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
public class CoordinatorLayoutSampleActivity extends BaseFontActivity {
    public static final String KEY = "KEY";
    public static final String VALUE_0 = "Standard App bar scrolling with only Toolbar";
    public static final String VALUE_1 = "App bar scrolling with tabs";
    public static final String VALUE_2 = "App bar scrolling with Flexible space";
    public static final String VALUE_3 = "App bar scrolling with overlapping content in Flexible space";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String value = getIntent().getStringExtra(KEY);
        switch (value) {
            case VALUE_0:
                LScreenUtil.INSTANCE.addFragment(getActivity(), R.id.fl_container, new FrmCoordinator0(), false);
                break;
            case VALUE_1:
                LScreenUtil.INSTANCE.addFragment(getActivity(), R.id.fl_container, new FrmCoordinator1(), false);
                break;
            case VALUE_2:
                LScreenUtil.INSTANCE.addFragment(getActivity(), R.id.fl_container, new FrmCoordinator2(), false);
                break;
            case VALUE_3:
                LScreenUtil.INSTANCE.addFragment(getActivity(), R.id.fl_container, new FrmCoordinator3(), false);
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
