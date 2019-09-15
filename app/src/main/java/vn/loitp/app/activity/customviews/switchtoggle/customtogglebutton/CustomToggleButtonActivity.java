package vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.views.switchtoggle.customtogglebutton.LCustomToggle;

import loitp.basemaster.R;

public class CustomToggleButtonActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LCustomToggle LCustomToggle1 = (LCustomToggle) findViewById(R.id.toggle_xml_1);
        //customToggle.addFirstIcon(R.drawable.l_ic_thumb_down_black_48dp);
        //customToggle.addSecondIcon(R.drawable.l_ic_thumb_up_black_48dp);
        //customToggle.setMagnification(9);
        //customToggle.setSlideBackgroundColor(Color.BLACK);
        //customToggle.setAnimationTime(700);
        //customToggle.setSlideColor(Color.GREEN);
        LCustomToggle1.setOnToggleClickListener(new LCustomToggle.OnToggleClickListener() {
            @Override
            public void onLefToggleEnabled(boolean enabled) {
                showShort("onLefToggleEnabled " + enabled);
            }

            @Override
            public void onRightToggleEnabled(boolean enabled) {
                showShort("onRightToggleEnabled " + enabled);
            }
        });

        LCustomToggle LCustomToggle2 = (LCustomToggle) findViewById(R.id.toggle_xml_2);
        LCustomToggle2.setOnToggleClickListener(new LCustomToggle.OnToggleClickListener() {
            @Override
            public void onLefToggleEnabled(boolean enabled) {
                showShort("onLefToggleEnabled " + enabled);
            }

            @Override
            public void onRightToggleEnabled(boolean enabled) {
                showShort("onRightToggleEnabled " + enabled);
            }
        });

        LCustomToggle LCustomToggle3 = (LCustomToggle) findViewById(R.id.toggle_xml_3);
        LCustomToggle3.setOnToggleClickListener(new LCustomToggle.OnToggleClickListener() {
            @Override
            public void onLefToggleEnabled(boolean enabled) {
                showShort("onLefToggleEnabled " + enabled);
            }

            @Override
            public void onRightToggleEnabled(boolean enabled) {
                showShort("onRightToggleEnabled " + enabled);
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
