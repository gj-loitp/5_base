package vn.loitp.app.activity.ads.admobbanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import vn.loitp.app.activity.animation.animationview.AnimationViewActivity;
import vn.loitp.app.activity.animation.overscroll.OverScrollActivity;
import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class AdmobBannerActivity extends BaseActivity {
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adView = (AdView) findViewById(R.id.adView);
        LUIUtil.createAdBanner(adView);

        TextView tv = (TextView) findViewById(R.id.tv);
        String poem = LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp);
        tv.setText(poem);
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
        return R.layout.activity_admob_banner;
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }
}
