package vn.loitp.app.activity.customviews.button;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.button.circularimageclick.CircularImageClickActivity;
import vn.loitp.app.activity.customviews.button.fbutton.FButtonActivity;
import vn.loitp.app.activity.customviews.button.shinebutton.ShineButtonActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;
import com.loitp.xwallpaper.R;

public class ButtonMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_shine_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ShineButtonActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_f_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, FButtonActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        findViewById(R.id.bt_circular_image_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, CircularImageClickActivity.class);
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
        return R.layout.activity_button_menu;
    }
}
