package vn.loitp.app.activity.customviews.switchtoggle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch.AppcompatSwitchActivity;
import vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton.CustomToggleButtonActivity;
import vn.loitp.app.activity.customviews.switchtoggle.toggle.ToggleActivity;

public class SwitchToggleMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_appcompat_switch).setOnClickListener(this);
        findViewById(R.id.bt_custom_toggle_button).setOnClickListener(this);
        findViewById(R.id.bt_toggle).setOnClickListener(this);
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
        return R.layout.activity_menu_switch_toggle;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_appcompat_switch:
                intent = new Intent(getActivity(), AppcompatSwitchActivity.class);
                break;
            case R.id.bt_custom_toggle_button:
                intent = new Intent(getActivity(), CustomToggleButtonActivity.class);
                break;
            case R.id.bt_toggle:
                intent = new Intent(getActivity(), ToggleActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.INSTANCE.tranIn(getActivity());
        }
    }
}
