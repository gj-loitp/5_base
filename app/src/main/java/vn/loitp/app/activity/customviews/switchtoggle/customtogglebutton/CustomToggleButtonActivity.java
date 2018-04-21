package vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.switchtoggle.customtogglebutton.lib.CustomToggle;

public class CustomToggleButtonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomToggle customToggle1 = (CustomToggle) findViewById(R.id.toggle_xml_1);
        //customToggle.addFirstIcon(R.drawable.ic_thumb_down_black_48dp);
        //customToggle.addSecondIcon(R.drawable.ic_thumb_up_black_48dp);
        //customToggle.setMagnification(9);
        //customToggle.setSlideBackgroundColor(Color.BLACK);
        //customToggle.setAnimationTime(700);
        //customToggle.setSlideColor(Color.GREEN);
        customToggle1.setOnToggleClickListener(new CustomToggle.OnToggleClickListener() {
            @Override
            public void onLefToggleEnabled(boolean enabled) {
                ToastUtils.showShort("onLefToggleEnabled " + enabled);
            }

            @Override
            public void onRightToggleEnabled(boolean enabled) {
                ToastUtils.showShort("onRightToggleEnabled " + enabled);
            }
        });

        CustomToggle customToggle2 = (CustomToggle) findViewById(R.id.toggle_xml_2);
        customToggle2.setOnToggleClickListener(new CustomToggle.OnToggleClickListener() {
            @Override
            public void onLefToggleEnabled(boolean enabled) {
                ToastUtils.showShort("onLefToggleEnabled " + enabled);
            }

            @Override
            public void onRightToggleEnabled(boolean enabled) {
                ToastUtils.showShort("onRightToggleEnabled " + enabled);
            }
        });

        CustomToggle customToggle3 = (CustomToggle) findViewById(R.id.toggle_xml_3);
        customToggle3.setOnToggleClickListener(new CustomToggle.OnToggleClickListener() {
            @Override
            public void onLefToggleEnabled(boolean enabled) {
                ToastUtils.showShort("onLefToggleEnabled " + enabled);
            }

            @Override
            public void onRightToggleEnabled(boolean enabled) {
                ToastUtils.showShort("onRightToggleEnabled " + enabled);
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
        return R.layout.activity_custom_toggle_button;
    }
}
