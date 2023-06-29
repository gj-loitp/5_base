package vn.loitp.up.a.demo.ad

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.MaxAdViewAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk
import com.applovin.sdk.AppLovinSdkUtils
import com.loitp.core.ext.e
import com.loitp.core.ext.i
import vn.loitp.BuildConfig
import vn.loitp.R

object Applovin {
    fun setupAd(c: Context) {
        // Please check config in gradle
        // Please add key in manifest

        // Initialize the AppLovin SDK
        AppLovinSdk.getInstance(c).mediationProvider = AppLovinMediationProvider.MAX
        showMediationDebugger(c)
        AppLovinSdk.getInstance(c).initializeSdk {
            // AppLovin SDK is initialized, start loading ads now or later if ad gate is reached
            e("Applovin", "setupAd initializeSdk $it")
            if (BuildConfig.DEBUG) {
                Toast.makeText(
                    c,
                    "Debug toast initializeSdk isTestModeEnabled: ${it.isTestModeEnabled}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun showMediationDebugger(c: Context) {
        if (BuildConfig.DEBUG) {
            AppLovinSdk.getInstance(c).showMediationDebugger()
        }
    }

    fun createAdBanner(
        a: Activity,
        logTag: String?,
        bkgColor: Int = Color.RED,
        viewGroup: ViewGroup,
        isAdaptiveBanner: Boolean
    ): MaxAdView {
        val log = "$logTag - createAdBanner"
        val adView = MaxAdView(a.getString(R.string.BANNER), a)
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
            ad.setRevenueListener {
                i(log, "onAdRevenuePaid")
            }

            if (isAdaptiveBanner) {
                // Stretch to the width of the screen for banners to be fully functional
                val width = ViewGroup.LayoutParams.MATCH_PARENT

                // Get the adaptive banner height.
                val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(a).height
                val heightPx = AppLovinSdkUtils.dpToPx(a, heightDp)

                ad.layoutParams = FrameLayout.LayoutParams(width, heightPx)
                ad.setExtraParameter("adaptive_banner", "true")
                ad.setLocalExtraParameter("adaptive_banner_width", 400)
                ad.adFormat.getAdaptiveSize(400, a).height // Set your ad height to this value
            } else {
                val isTablet = AppLovinSdkUtils.isTablet(a)
                val heightPx = AppLovinSdkUtils.dpToPx(a, if (isTablet) 90 else 50)

                ad.layoutParams = FrameLayout.LayoutParams(
                    /* width = */ ViewGroup.LayoutParams.MATCH_PARENT,
                    /* height = */ heightPx
                )
            }

            ad.setBackgroundColor(bkgColor)
            viewGroup.addView(adView)
            ad.loadAd()
        }
        return adView
    }
}
