package vn.loitp.up.a.demo.ad.banner

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdRevenueListener
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAdBannerBinding

@LogTag("BannerActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class BannerActivity : BaseActivityFont() {

    private lateinit var binding: AAdBannerBinding
    private var adView: MaxAdView? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAdBannerBinding.inflate(layoutInflater)
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

        adView = MaxAdView("YOUR_AD_UNIT_ID", this)
        adView?.let { ad ->
            ad.setListener(object : MaxAdViewAdListener {
                override fun onAdLoaded(p0: MaxAd?) {
                    logI("onAdLoaded")
                }

                override fun onAdDisplayed(p0: MaxAd?) {
                    logI("onAdLoaded")
                }

                override fun onAdHidden(p0: MaxAd?) {
                    logI("onAdHidden")
                }

                override fun onAdClicked(p0: MaxAd?) {
                    logI("onAdClicked")
                }

                override fun onAdLoadFailed(p0: String?, p1: MaxError?) {
                    logI("onAdLoadFailed")
                }

                override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError?) {
                    logI("onAdDisplayFailed")
                }

                override fun onAdExpanded(p0: MaxAd?) {
                    logI("onAdExpanded")
                }

                override fun onAdCollapsed(p0: MaxAd?) {
                    logI("onAdCollapsed")
                }

            })

            val isTablet = AppLovinSdkUtils.isTablet(this)
            val heightPx = AppLovinSdkUtils.dpToPx(this, if (isTablet) 90 else 50)

            ad.layoutParams = FrameLayout.LayoutParams(
                /* width = */ ViewGroup.LayoutParams.MATCH_PARENT,
                /* height = */ heightPx
            )
            ad.setBackgroundColor(Color.RED)

            val rootView = findViewById<ViewGroup>(android.R.id.content)
            rootView.addView(adView)

            ad.loadAd()
        }
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }


}
