package vn.loitp.app.activity.ads.admobrewardedvideo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class AdmobRewardedVideoActivity extends BaseActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mAd;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    private RelativeLayout llMain;
    private LinearLayout llAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isShowAdWhenExist = false;
        llMain = (RelativeLayout) findViewById(R.id.root_view);

        llAd = (LinearLayout) findViewById(R.id.ll_ad);
        llAd.setVisibility(View.GONE);

        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);
        MobileAds.initialize(this, getString(R.string.app_id));
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        llAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRewardAd();
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
        return R.layout.activity_admob_rewarded_video;
    }

    private void loadRewardedVideoAd() {
        //LLog.d(TAG, "loadRewardedVideoAd");
        avLoadingIndicatorView.smoothToShow();
        llAd.setVisibility(View.GONE);
        mAd.loadAd(getString(R.string.str_reward), new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("6E0762FF2B272D5BCE89FEBAAB872E34")
                .addTestDevice("8FA8E91902B43DCB235ED2F6BBA9CAE0")
                .addTestDevice("58844B2E50AF6E33DC818387CC50E593")
                .addTestDevice("179198315EB7B069037C5BE8DEF8319A")
                .addTestDevice("7DA8A5B216E868636B382A7B9756A4E6")
                .addTestDevice("A1EC01C33BD69CD589C2AF605778C2E6")
                .addTestDevice("13308851AEDCA44443112D80A8D182CA")
                .build());
    }

    private void displayRewardAd() {
        LLog.d(TAG, "displayRewardAd isLoaded: " + mAd.isLoaded());
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            loadRewardedVideoAd();
        }
    }

    @Override
    public void onRewarded(RewardItem reward) {
        LLog.d(TAG, "onRewarded");
        onBackPressed();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        LLog.d(TAG, "onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        LLog.d(TAG, "onRewardedVideoAdClosed");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        LLog.d(TAG, "onRewardedVideoAdFailedToLoad " + errorCode);
        ToastUtils.showShort("onRewardedVideoAdFailedToLoad code: " + errorCode);
        onBackPressed();
    }

    @Override
    public void onRewardedVideoCompleted() {
        LLog.d(TAG, "onRewardedVideoCompleted");
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        LLog.d(TAG, "onRewardedVideoAdLoaded");
        avLoadingIndicatorView.smoothToHide();
        llAd.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {
        LLog.d(TAG, "onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoStarted() {
        LLog.d(TAG, "onRewardedVideoStarted");
    }
}
