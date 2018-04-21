package vn.loitp.app.activity.ads.admobinterstitial;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;

public class AdmobInterstitialActivity extends BaseActivity {
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isShowAdWhenExist = false;//remove show duplicate ads in BaseActivity

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
    protected int setLayoutResourceId() {
        return R.layout.activity_admob_interstitial;
    }

    @Override
    public void onBackPressed() {
        LUIUtil.displayInterstitial(interstitialAd);
        super.onBackPressed();
    }
}
