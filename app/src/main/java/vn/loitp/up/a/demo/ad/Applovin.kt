package vn.loitp.up.a.demo.ad

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkUtils
import com.loitp.core.ext.i
import vn.loitp.R

object Applovin {
    fun setupAd(c: Context) {
        // Initialize the AppLovin SDK
        AppLovinSdk.getInstance(c).mediationProvider = AppLovinMediationProvider.MAX
        AppLovinSdk.getInstance(c).initializeSdk {
            // AppLovin SDK is initialized, start loading ads now or later if ad gate is reached

        }
    }

    fun createAdBanner(
        c: Context,
        logTag: String?,
        bkgColor: Int = Color.RED,
        viewGroup: ViewGroup
    ): MaxAdView {
        val log = "$logTag - createAdBanner"
        val adView = MaxAdView(c.getString(R.string.BANNER), c)
        adView.let { ad ->
            ad.setListener(object : MaxAdViewAdListener {
                override fun onAdLoaded(p0: MaxAd?) {
                    i(log, "onAdLoaded")
                }

                override fun onAdDisplayed(p0: MaxAd?) {
                    i(log, "onAdDisplayed")
                }

                override fun onAdHidden(p0: MaxAd?) {
                    i(log, "onAdHidden")
                }

                override fun onAdClicked(p0: MaxAd?) {
                    i(log, "onAdClicked")
                }

                override fun onAdLoadFailed(p0: String?, p1: MaxError?) {
                    i(log, "onAdLoadFailed")
                }

                override fun onAdDisplayFailed(p0: MaxAd?, p1: MaxError?) {
                    i(log, "onAdDisplayFailed")
                }

                override fun onAdExpanded(p0: MaxAd?) {
                    i(log, "onAdExpanded")
                }

                override fun onAdCollapsed(p0: MaxAd?) {
                    i(log, "onAdCollapsed")
                }

            })

            val isTablet = AppLovinSdkUtils.isTablet(c)
            val heightPx = AppLovinSdkUtils.dpToPx(c, if (isTablet) 90 else 50)

            ad.layoutParams = FrameLayout.LayoutParams(
                /* width = */ ViewGroup.LayoutParams.MATCH_PARENT,
                /* height = */ heightPx
            )
            ad.setBackgroundColor(bkgColor)
            viewGroup.addView(adView)
            ad.loadAd()
        }
        return adView
    }
}
