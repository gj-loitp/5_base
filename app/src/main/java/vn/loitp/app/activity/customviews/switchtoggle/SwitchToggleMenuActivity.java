package vn.loitp.app.activity.customviews.switchtoggle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch.AppcompatSwitchActivity;
import vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton.CustomToggleButtonActivity;
import vn.loitp.app.activity.customviews.switchtoggle.toggle.ToggleActivity;
import vn.loitp.core.base.BaseActivity;

import loitp.basemaster.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class SwitchToggleMenuActivity extends BaseActivity implements View.OnClickListener {

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
                intent = new Intent(activity, AppcompatSwitchActivity.class);
                break;
            case R.id.bt_custom_toggle_button:
                intent = new Intent(activity, CustomToggleButtonActivity.class);
                break;
            case R.id.bt_toggle:
                intent = new Intent(activity, ToggleActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
