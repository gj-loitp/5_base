package vn.loitp.up.a.demo.ad.banner

import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdmobBannerBinding

@LogTag("BannerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class BannerActivity : BaseActivityFont() {

    private lateinit var binding: AAdmobBannerBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdmobBannerBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = BannerActivity::class.java.simpleName
        }

        setupAdmob()
    }

    private fun setupAdmob() {
        // Log the Mobile Ads SDK version.
        logE("Google Mobile Ads SDK Version: " + MobileAds.getVersion())
        // Initialize the Mobile Ads SDK with an AdMob App ID.
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
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        binding.adView.loadAd(adRequest)
    }

    public override fun onPause() {
        binding.adView.pause()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        binding.adView.resume()
    }

    public override fun onDestroy() {
        binding.adView.destroy()
        super.onDestroy()
    }
}
