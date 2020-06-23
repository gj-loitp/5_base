package com.core.helper.admobrewardedvideo

import android.os.Bundle
import com.R
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_admob_rewarded_video.*

class AdmobRewardedVideoActivity : BaseFontActivity(), RewardedVideoAdListener {
    private var mAd: RewardedVideoAd? = null
    private var strReward: String? = null

    companion object {
        const val APP_ID = "APP_ID"
        const val ID_REWARD = "ID_REWARD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView.setText(R.string.loading)
        LUIUtil.setTextShadow(textView)

        animationView.setAnimation("lottie/gradient_animated_background.json")
        //lottieAnimationView.useHardwareAcceleration();
        //lottieAnimationView.setScale(0.3f);
        animationView.playAnimation()
        animationView.loop(true)

        animationViewGift.setAnimation("lottie/happy_gift.json")
        //animationViewGift.useHardwareAcceleration();
        //animationViewGift.setScale(0.3f);
        //animationViewGift.playAnimation();
        animationViewGift.loop(true)

        val strAppId = intent.getStringExtra(APP_ID)
        strReward = intent.getStringExtra(ID_REWARD)
        if (strAppId.isNullOrEmpty() || strReward.isNullOrEmpty()) {
            showShort(getString(R.string.err_unknow))
            onBackPressed()
            return
        }

        MobileAds.initialize(this, strAppId)
        mAd = MobileAds.getRewardedVideoAdInstance(this)
        mAd?.rewardedVideoAdListener = this
        loadRewardedVideoAd()

        rootView.setSafeOnClickListener { displayRewardAd() }
    }

    override fun setFullScreen(): Boolean {
        return true
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_admob_rewarded_video
    }

    private fun loadRewardedVideoAd() {
        //LLog.d(TAG, "loadRewardedVideoAd");
        //avLoadingIndicatorView.smoothToShow();
        textView.setText(R.string.loading)
        mAd?.loadAd(strReward, AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("6E0762FF2B272D5BCE89FEBAAB872E34")
                .addTestDevice("8FA8E91902B43DCB235ED2F6BBA9CAE0")
                .addTestDevice("58844B2E50AF6E33DC818387CC50E593")
                .addTestDevice("179198315EB7B069037C5BE8DEF8319A")
                .addTestDevice("7DA8A5B216E868636B382A7B9756A4E6")
                .addTestDevice("A1EC01C33BD69CD589C2AF605778C2E6")
                .addTestDevice("13308851AEDCA44443112D80A8D182CA")
                .build())
    }

    private fun displayRewardAd() {
        logD("displayRewardAd isLoaded: " + mAd?.isLoaded)
        if (mAd?.isLoaded == true) {
            mAd?.show()
        } else {
            loadRewardedVideoAd()
        }
    }

    override fun onRewarded(reward: RewardItem) {
        logD("onRewarded")
        onBackPressed()
    }

    override fun onRewardedVideoAdLeftApplication() {
        logD("onRewardedVideoAdLeftApplication")
    }

    override fun onRewardedVideoAdClosed() {
        logD("onRewardedVideoAdClosed")
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        logD("onRewardedVideoAdFailedToLoad $errorCode")
        onBackPressed()
    }

    override fun onRewardedVideoCompleted() {
        logD("onRewardedVideoCompleted")
    }

    override fun onRewardedVideoAdLoaded() {
        logD("onRewardedVideoAdLoaded")
        textView.setText(R.string.open_gift)
        LAnimationUtil.play(rootView, Techniques.Pulse)
        animationViewGift.playAnimation()
    }

    override fun onRewardedVideoAdOpened() {
        logD("onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoStarted() {
        logD("onRewardedVideoStarted")
    }
}
