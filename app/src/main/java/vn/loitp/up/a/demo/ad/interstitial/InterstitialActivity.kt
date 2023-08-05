package vn.loitp.up.a.demo.ad.interstitial

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.google.android.gms.ads.*
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.getRandomNumber
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.databinding.AAdInterstitialBinding
import vn.loitp.up.app.EmptyActivity
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.pow

@LogTag("InterstitialActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class InterstitialActivity : BaseActivityFont() {
    private lateinit var binding: AAdInterstitialBinding
    private var interstitialAd: MaxInterstitialAd? = null
    private var retryAttempt = 0

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdInterstitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = InterstitialActivity::class.java.simpleName
        }
        binding.btShow.setSafeOnClickListener {
            showAd {
                launchActivity(EmptyActivity::class.java)
            }
        }

        createAdInter()
    }

    private fun createAdInter() {
        val enableAdInter = getString(R.string.EnableAdInter) == "true"
        if (enableAdInter) {
            interstitialAd = MaxInterstitialAd(getString(R.string.INTER), this)
            interstitialAd?.let { ad ->
                ad.setListener(object : MaxAdListener {
                    override fun onAdLoaded(p0: MaxAd?) {
                        logI("onAdLoaded")
                        retryAttempt = 0
                    }

                    override fun onAdDisplayed(p0: MaxAd?) {
                        logI("onAdDisplayed")
                    }

                    override fun onAdHidden(p0: MaxAd?) {
                        logI("onAdHidden")
                        // Interstitial Ad is hidden. Pre-load the next ad
                        interstitialAd?.loadAd()
                    }

                    override fun onAdClicked(p0: MaxAd?) {
                        logI("onAdClicked")
                    }

                    override fun onAdLoadFailed(p0: String?, p1: MaxError?) {
                        logI("onAdLoadFailed")
                        retryAttempt++
                        val delayMillis =
                            TimeUnit.SECONDS.toMillis(2.0.pow(min(6, retryAttempt)).toLong())

                        Handler(Looper.getMainLooper()).postDelayed(
                            {
                                interstitialAd?.loadAd()
                            }, delayMillis
                        )
                    }

                    override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError?) {
                        logI("onAdDisplayFailed")
                        // Interstitial ad failed to display. We recommend loading the next ad.
                        interstitialAd?.loadAd()
                    }

                })
                ad.setRevenueListener {
                    logI("onAdDisplayed")
                }

                // Load the first ad.
                ad.loadAd()
            }
        }
    }

    private fun showAd(runnable: Runnable? = null) {
        val enableAdInter = getString(R.string.EnableAdInter) == "true"
        if (enableAdInter) {
            if (interstitialAd == null) {
                runnable?.run()
            } else {
                interstitialAd?.let { ad ->
                    if (ad.isReady) {
                        showDialogProgress()
                        setDelay(500.getRandomNumber() + 500) {
                            hideDialogProgress()
                            ad.showAd()
                            runnable?.run()
                        }
                    } else {
                        runnable?.run()
                    }
                }
            }
        } else {
            runnable?.run()
        }
    }

}
