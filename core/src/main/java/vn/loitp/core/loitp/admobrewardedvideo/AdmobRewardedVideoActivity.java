package vn.loitp.core.loitp.admobrewardedvideo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.androidanimations.library.Techniques;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

public class AdmobRewardedVideoActivity extends BaseFontActivity implements RewardedVideoAdListener {
    private RewardedVideoAd mAd;
    //private AVLoadingIndicatorView avLoadingIndicatorView;
    public final static String APP_ID = "APP_ID";
    public final static String ID_REWARD = "ID_REWARD";
    private TextView tv;
    private String strAppId;
    private String strReward;
    private LottieAnimationView lottieAnimationViewGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        tv = (TextView) findViewById(R.id.tv);
        tv.setVisibility(View.GONE);
        LUIUtil.setTextShadow(tv);
        //avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);
        lottieAnimationView.setAnimation("lottie/gradient_animated_background.json");
        lottieAnimationView.useHardwareAcceleration();
        //lottieAnimationView.setScale(0.3f);
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(true);

        lottieAnimationViewGift = (LottieAnimationView) findViewById(R.id.animation_view_gift);
        lottieAnimationViewGift.setAnimation("lottie/happy_gift.json");
        lottieAnimationViewGift.useHardwareAcceleration();
        //lottieAnimationView.setScale(0.3f);
        //lottieAnimationViewGift.playAnimation();
        lottieAnimationViewGift.loop(true);

        strAppId = getIntent().getStringExtra(APP_ID);
        strReward = getIntent().getStringExtra(ID_REWARD);
        if (strAppId == null || strAppId.isEmpty() || strReward == null || strReward.isEmpty()) {
            LToast.show(activity, getString(R.string.err_unknow));
            onBackPressed();
            return;
        }

        MobileAds.initialize(this, strAppId);
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRewardAd();
            }
        });
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
    protected int setLayoutResourceId() {
        return R.layout.activity_admob_rewarded_video;
    }

    private void loadRewardedVideoAd() {
        //LLog.d(TAG, "loadRewardedVideoAd");
        //avLoadingIndicatorView.smoothToShow();
        tv.setVisibility(View.GONE);
        mAd.loadAd(strReward, new AdRequest.Builder()
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
        //ToastUtils.showShort("onRewardedVideoAdFailedToLoad code: " + errorCode);
        onBackPressed();
    }

    @Override
    public void onRewardedVideoCompleted() {
        LLog.d(TAG, "onRewardedVideoCompleted");
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        LLog.d(TAG, "onRewardedVideoAdLoaded");
        //avLoadingIndicatorView.smoothToHide();
        tv.setVisibility(View.VISIBLE);
        LAnimationUtil.play(getRootView(), Techniques.Pulse);
        lottieAnimationViewGift.playAnimation();
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
