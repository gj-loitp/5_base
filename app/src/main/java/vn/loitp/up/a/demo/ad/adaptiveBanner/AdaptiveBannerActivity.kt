package vn.loitp.up.a.demo.ad.adaptiveBanner

import android.os.Bundle
import android.util.DisplayMetrics
import com.google.android.gms.ads.*
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.BuildConfig
import vn.loitp.databinding.AAdmobAdaptiveBannerBinding

@LogTag("AdaptiveBannerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class AdaptiveBannerActivity : BaseActivityFont() {

    private lateinit var binding: AAdmobAdaptiveBannerBinding
    private var adView: AdView? = null
    private var initialLayoutComplete = false

    // Determine the screen width (less decorations) to use for the ad width.
    // If the ad hasn't been laid out, default to the full screen width.
    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.adViewContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdmobAdaptiveBannerBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = AdaptiveBannerActivity::class.java.simpleName
        }

        setupAdmob()
    }

    private fun setupAdmob() {
//        Log the Mobile Ads SDK version .
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
                    AdRequest.DEVICE_ID_EMULATOR,
                    getString(R.string.admob_test_device_id_lg_v60),
                    getString(R.string.admob_test_device_id_samsung_a50),
                )
            ).build()
        )
        adView = AdView(this)
        binding.adViewContainer.addView(adView)
        // Since we're loading the banner based on the adContainerView size, we need to wait until this
        // view is laid out before we can get the width.
        binding.adViewContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }
    }

    private fun loadBanner() {
        adView?.let { ad ->
            ad.adUnitId = getString(R.string.admob_adaptive_banner_id)
            ad.setAdSize(adSize)
            // Create an ad request.
            val adRequest = AdRequest.Builder().build()
            // Start loading the ad in the background.
            ad.loadAd(adRequest)
        }
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        adView?.resume()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
