package vn.loitp.app.activity.customviews.switchtoggle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch.AppcompatSwitchActivity;
import vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton.CustomToggleButtonActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import com.loitp.xwallpaper.R;

public class SwitchToggleMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_appcompat_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AppcompatSwitchActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_custom_toggle_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CustomToggleButtonActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_switch_toggle;
    }
}
