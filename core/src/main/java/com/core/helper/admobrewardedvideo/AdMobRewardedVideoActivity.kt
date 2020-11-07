package com.core.helper.admobrewardedvideo

import android.annotation.SuppressLint
import android.os.Bundle
import com.R
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_activity_admob_rewarded_video.*


@LogTag("AdMobRewardedVideoActivity")
class AdMobRewardedVideoActivity : BaseFontActivity() {
    private var rewardedAd: RewardedAd? = null
    private var strReward: String? = null

    companion object {
        const val APP_ID = "APP_ID"
        const val ID_REWARD = "ID_REWARD"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_admob_rewarded_video
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
            showShortError(getString(R.string.err_unknow))
            onBackPressed()
            return
        }
        loadRewardedVideoAd()
        rootView.setSafeOnClickListener {
            displayRewardAd()
        }
    }

    private fun loadRewardedVideoAd() {
        textView.text = getString(R.string.loading)
        rewardedAd = RewardedAd(this, strReward)

        val adLoadCallback: RewardedAdLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                logD("onRewardedAdLoaded")
//                displayRewardAd()
                textView.setText(R.string.open_gift)
                LAnimationUtil.play(view = rootView, techniques = Techniques.Pulse)
                animationViewGift.playAnimation()
            }

            @SuppressLint("SetTextI18n")
            override fun onRewardedAdFailedToLoad(adError: LoadAdError) {
                logD("onRewardedAdFailedToLoad adError $adError")
                textView.text = "onRewardedAdFailedToLoad\n${adError.message}"
            }
        }
        rewardedAd?.loadAd(AdRequest.Builder().build(), adLoadCallback)
    }

    private fun displayRewardAd() {
        if (rewardedAd?.isLoaded == true) {
            rewardedAd?.show(this, object : RewardedAdCallback() {

                override fun onUserEarnedReward(p0: com.google.android.gms.ads.rewarded.RewardItem) {
                    logD("onUserEarnedReward")
                }

                override fun onRewardedAdOpened() {
                    super.onRewardedAdOpened()
                    logD("onRewardedAdOpened")
                }

                override fun onRewardedAdClosed() {
                    super.onRewardedAdClosed()
                    logD("onRewardedAdClosed")
                }

                override fun onRewardedAdFailedToShow(adError: AdError?) {
                    super.onRewardedAdFailedToShow(adError)
                    logD("onRewardedAdFailedToShow adError $adError")
                }
            })
        } else {
            showLongInformation("Ad is not ready")
        }
    }
}
