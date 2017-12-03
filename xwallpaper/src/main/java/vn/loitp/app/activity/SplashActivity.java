package vn.loitp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.utilities.LUIUtil;

import com.loitp.xwallpaper.BuildConfig;
import com.loitp.xwallpaper.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LUIUtil.setDelay(2500, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                Intent intent = new Intent(activity, MenuActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
                finish();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("Version " + BuildConfig.VERSION_NAME);
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
        return R.layout.activity_splash;
    }
}
