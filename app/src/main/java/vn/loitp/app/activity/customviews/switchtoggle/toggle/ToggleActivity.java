package vn.loitp.app.activity.customviews.switchtoggle.toggle;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.views.LToast;
import com.views.switchtoggle.toggle.LabeledSwitch;
import com.views.switchtoggle.toggle.interfaces.OnToggledListener;

import loitp.basemaster.R;

//https://github.com/Angads25/android-toggle?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6778
public class ToggleActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LabeledSwitch labeledSwitch = findViewById(R.id.ls);
        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                LToast.INSTANCE.show(getActivity(), "isOn " + isOn);
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
