package vn.loitp.app.activity.ads.admobinterstitial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LStoreUtil;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class AdmobInterstitialActivity extends BaseActivity {
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        interstitialAd = LUIUtil.createAdFull(activity);

        TextView tv = (TextView) findViewById(R.id.tv);
        String s = LStoreUtil.readTxtFromRawFolder(activity, R.raw.ad_full);
        tv.setText(s);
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
        return R.layout.activity_admob_interstitial;
    }

    @Override
    public void onBackPressed() {
        LUIUtil.displayInterstitial(interstitialAd);
        super.onBackPressed();
    }
}
