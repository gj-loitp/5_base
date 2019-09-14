package com.core.loitp.admobrewardedvideo

import android.os.Bundle
import android.widget.TextView
import com.R

import com.airbnb.lottie.LottieAnimationView
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.views.LToast

class AdmobRewardedVideoActivity : BaseFontActivity(), RewardedVideoAdListener {
    private var mAd: RewardedVideoAd? = null
    private var tv: TextView? = null
    private var strReward: String? = null
    private var lottieAnimationViewGift: LottieAnimationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        tv = findViewById(R.id.tv)
        tv?.let {
            it.setText(R.string.loading)
            LUIUtil.setTextShadow(it)
        }
        //avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avi);

        val lottieAnimationView = findViewById<LottieAnimationView>(R.id.animation_view)
        lottieAnimationView.setAnimation("lottie/gradient_animated_background.json")
        //lottieAnimationView.useHardwareAcceleration();
        //lottieAnimationView.setScale(0.3f);
        lottieAnimationView.playAnimation()
        lottieAnimationView.loop(true)

        lottieAnimationViewGift = findViewById(R.id.animation_view_gift)
        lottieAnimationViewGift?.setAnimation("lottie/happy_gift.json")
        //lottieAnimationViewGift.useHardwareAcceleration();
        //lottieAnimationView.setScale(0.3f);
        //lottieAnimationViewGift.playAnimation();
        lottieAnimationViewGift?.loop(true)

        val strAppId = intent.getStringExtra(APP_ID)
        strReward = intent.getStringExtra(ID_REWARD)
        if (strAppId.isNullOrEmpty() || strReward.isNullOrEmpty()) {
            LToast.show(activity, getString(R.string.err_unknow))
            onBackPressed()
            return
        }

        MobileAds.initialize(this, strAppId)
        mAd = MobileAds.getRewardedVideoAdInstance(this)
        mAd?.rewardedVideoAdListener = this
        loadRewardedVideoAd()

        rootView?.setOnClickListener { displayRewardAd() }
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
        tv?.setText(R.string.loading)
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
        LLog.d(TAG, "displayRewardAd isLoaded: " + mAd?.isLoaded)
        if (mAd?.isLoaded == true) {
            mAd?.show()
        } else {
            loadRewardedVideoAd()
        }
    }

    override fun onRewarded(reward: RewardItem) {
        LLog.d(TAG, "onRewarded")
        onBackPressed()
    }

    override fun onRewardedVideoAdLeftApplication() {
        LLog.d(TAG, "onRewardedVideoAdLeftApplication")
    }

    override fun onRewardedVideoAdClosed() {
        LLog.d(TAG, "onRewardedVideoAdClosed")
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        LLog.d(TAG, "onRewardedVideoAdFailedToLoad $errorCode")
        onBackPressed()
    }

    override fun onRewardedVideoCompleted() {
        LLog.d(TAG, "onRewardedVideoCompleted")
    }

    override fun onRewardedVideoAdLoaded() {
        LLog.d(TAG, "onRewardedVideoAdLoaded")
        //avLoadingIndicatorView.smoothToHide();
        tv?.setText(R.string.open_gift)
        LAnimationUtil.play(rootView, Techniques.Pulse)
        lottieAnimationViewGift?.playAnimation()
    }

    override fun onRewardedVideoAdOpened() {
        LLog.d(TAG, "onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoStarted() {
        LLog.d(TAG, "onRewardedVideoStarted")
    }

    companion object {
        //private AVLoadingIndicatorView avLoadingIndicatorView;
        const val APP_ID = "APP_ID"
        const val ID_REWARD = "ID_REWARD"
    }
}
