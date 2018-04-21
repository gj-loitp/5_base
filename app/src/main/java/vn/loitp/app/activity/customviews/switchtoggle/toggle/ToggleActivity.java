package vn.loitp.app.activity.customviews.switchtoggle.toggle;

import android.app.Activity;
import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.LToast;
import vn.loitp.views.switchtoggle.customtogglebutton.lib.CustomToggle;
import vn.loitp.views.switchtoggle.toggle.LabeledSwitch;
import vn.loitp.views.switchtoggle.toggle.interfaces.OnToggledListener;

//https://github.com/Angads25/android-toggle?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6778
public class ToggleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LabeledSwitch labeledSwitch = findViewById(R.id.ls);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                LToast.show(activity, "isOn " + isOn);
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
        return R.layout.activity_toggle;
    }
}
