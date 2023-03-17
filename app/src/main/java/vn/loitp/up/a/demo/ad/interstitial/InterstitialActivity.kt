package vn.loitp.up.a.demo.ad.interstitial

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.view.isVisible
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdmobInterstitialBinding

@LogTag("InterstitialActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class InterstitialActivity : BaseActivityFont() {
    private val GAME_LENGTH_MILLISECONDS = 3000L
    private lateinit var binding: AAdmobInterstitialBinding
    private var interstitialAd: InterstitialAd? = null
    private var countdownTimer: CountDownTimer? = null
    private var gameIsInProgress = false
    private var adIsLoading: Boolean = false
    private var timerMilliseconds = 0L

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdmobInterstitialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = InterstitialActivity::class.java.simpleName
        }

        setupAdmob()

        // Create the "retry" button, which triggers an interstitial between game plays.
        binding.retryButton.isVisible = false
        binding.retryButton.setOnClickListener {
            showInterstitial()
        }

        // Kick off the first play of the "game."
        startGame()
    }

    private fun setupAdmob() {
        logE("Google Mobile Ads SDK Version: " + MobileAds.getVersion())

        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {}

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(
                listOf(
                    getString(R.string.admob_test_device_id_lg_v60),
                    getString(R.string.admob_test_device_id_samsung_a50),
                )
            ).build()
        )
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            /* context = */ this,
            /* adUnitId = */ getString(R.string.admob_interstitial_id),
            /* adRequest = */ adRequest,
            /* loadCallback = */ object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    logE(adError.message)
                    interstitialAd = null
                    adIsLoading = false
                    val error =
                        "domain: ${adError.domain}, code: ${adError.code}, " + "message: ${adError.message}"
                    showShortError("onAdFailedToLoad() with error $error")
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    logD("Ad was loaded")
                    interstitialAd = ad
                    adIsLoading = false
                    showShortInformation("onAdLoaded()")
                }
            }
        )
    }

    // Create the game timer, which counts down to the end of the level
    // and shows the "retry" button.
    @SuppressLint("SetTextI18n")
    private fun createTimer(milliseconds: Long) {
        countdownTimer?.cancel()
        countdownTimer =
            object : CountDownTimer(milliseconds, 50) {
                override fun onTick(millisUntilFinished: Long) {
                    timerMilliseconds = millisUntilFinished
                    binding.timer.text = "seconds remaining: ${millisUntilFinished / 1000 + 1}"
                }

                override fun onFinish() {
                    gameIsInProgress = false
                    binding.timer.text = "done!"
                    binding.retryButton.visibility = View.VISIBLE
                }
            }
    }

    // Show the ad if it's ready. Otherwise toast and restart the game.
    private fun showInterstitial() {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        showShortInformation("Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        interstitialAd = null
                        loadAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        showShortInformation("Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        interstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        showShortInformation("Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }
            interstitialAd?.show(this)
        } else {
            showShortError("Ad wasn't loaded.")
            startGame()
        }
    }

    // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
    private fun startGame() {
        if (!adIsLoading && interstitialAd == null) {
            adIsLoading = true
            loadAd()
        }

        binding.retryButton.isVisible = false
        resumeGame(GAME_LENGTH_MILLISECONDS)
    }

    private fun resumeGame(milliseconds: Long) {
        // Create a new timer for the correct length and start it.
        gameIsInProgress = true
        timerMilliseconds = milliseconds
        createTimer(milliseconds)
        countdownTimer?.start()
    }

    // Resume the game if it's in progress.
    public override fun onResume() {
        super.onResume()

        if (gameIsInProgress) {
            resumeGame(timerMilliseconds)
        }
    }

    // Cancel the timer if the game is paused.
    public override fun onPause() {
        countdownTimer?.cancel()
        super.onPause()
    }
}
