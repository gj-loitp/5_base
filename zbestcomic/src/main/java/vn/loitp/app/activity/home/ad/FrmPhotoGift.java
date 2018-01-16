package vn.loitp.app.activity.home.ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmPhotoGift extends BaseFragment implements RewardedVideoAdListener {
    private final String TAG = getClass().getSimpleName();
    private RewardedVideoAd mAd;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    private RelativeLayout llMain;
    private LinearLayout llAd;
    private TextView tvGiftNotFound;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_photo_gift, container, false);
        llMain = (RelativeLayout) view.findViewById(R.id.root_view);
        tvGiftNotFound = (TextView) view.findViewById(R.id.tv_gift_not_found);

        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        LUIUtil.setImageFromAsset(getActivity(), "ic_gift.png", iv);

        llAd = (LinearLayout) view.findViewById(R.id.ll_ad);
        llAd.setVisibility(View.GONE);

        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        MobileAds.initialize(getActivity(), getString(R.string.app_id));
        mAd = MobileAds.getRewardedVideoAdInstance(getActivity());
        mAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        llAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayRewardAd();
            }
        });
        return view;
    }

    private void loadRewardedVideoAd() {
        //LLog.d(TAG, "loadRewardedVideoAd");
        avLoadingIndicatorView.smoothToShow();
        llAd.setVisibility(View.GONE);
        mAd.loadAd(getString(R.string.str_reward), new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(Constants.TEST_0)
                .addTestDevice(Constants.TEST_1)
                .addTestDevice(Constants.TEST_2)
                .addTestDevice(Constants.TEST_3)
                .addTestDevice(Constants.TEST_4)
                .addTestDevice(Constants.TEST_5)
                .addTestDevice(Constants.TEST_6)
                .build());
    }

    private void displayRewardAd() {
        //LLog.d(TAG, "displayRewardAd isLoaded: " + mAd.isLoaded());
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            loadRewardedVideoAd();
        }
    }

    @Override
    public void onRewarded(RewardItem reward) {
        //LLog.d(TAG, "onRewarded");
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        //LLog.d(TAG, "onRewardedVideoAdLeftApplication");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        //LLog.d(TAG, "onRewardedVideoAdClosed");
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        //LLog.d(TAG, "onRewardedVideoAdFailedToLoad " + errorCode);
        //ToastUtils.showShort("onRewardedVideoAdFailedToLoad code: " + errorCode);
        tvGiftNotFound.setVisibility(View.VISIBLE);
        avLoadingIndicatorView.smoothToHide();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        //LLog.d(TAG, "onRewardedVideoAdLoaded");
        avLoadingIndicatorView.smoothToHide();
        llAd.setVisibility(View.VISIBLE);
        tvGiftNotFound.setVisibility(View.GONE);
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