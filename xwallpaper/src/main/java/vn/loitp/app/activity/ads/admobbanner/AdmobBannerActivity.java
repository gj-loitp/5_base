package vn.loitp.app.activity.ads.admobbanner;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.loitp.xwallpaper.R;

import vn.loitp.core.base.BaseActivity;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;

public class AdmobBannerActivity extends BaseActivity {
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isShowAdWhenExist = false;

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
