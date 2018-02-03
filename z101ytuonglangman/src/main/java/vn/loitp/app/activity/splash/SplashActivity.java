package vn.loitp.app.activity.splash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.HomeMenuActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class SplashActivity extends BaseActivity {
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        interstitialAd = LUIUtil.createAdFull(activity);
        LActivityUtil.hideSystemUI(getWindow().getDecorView());

        ImageView ivBkg = (ImageView) findViewById(R.id.iv_bkg);
        LUIUtil.setImageFromAsset(activity, "bkg.JPG", ivBkg);

        TextView tvAppName = (TextView) findViewById(R.id.tv_app_name);
        LUIUtil.setTextShadow(tvAppName, Color.WHITE);

        LUIUtil.setDelay(2500, new LUIUtil.DelayCallback() {
            @Override
            public void doAfter(int mls) {
                goToHome();
            }
        });
    }

    private void goToHome() {
        Intent intent = new Intent(activity, HomeMenuActivity.class);
        startActivity(intent);
        LActivityUtil.tranIn(activity);
        LUIUtil.displayInterstitial(interstitialAd, 100);
        finish();
    }

    @Override
    protected boolean setFullScreen() {
        return true;
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
