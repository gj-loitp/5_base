package vn.loitp.up.a.demo.ad

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
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

fun Context.setupApplovinAd() {
    // Please check config in gradle
    // Please add key in manifest

    // Initialize the AppLovin SDK
    AppLovinSdk.getInstance(this).mediationProvider = AppLovinMediationProvider.MAX
//        showMediationDebugger(c)
    AppLovinSdk.getInstance(this).initializeSdk {
        // AppLovin SDK is initialized, start loading ads now or later if ad gate is reached
        e("Applovin", "setupAd initializeSdk $it")
        if (BuildConfig.DEBUG) {
            Toast.makeText(
                /* context = */ this,
                /* text = */ "Debug toast initializeSdk isTestModeEnabled: ${it.isTestModeEnabled}",
                /* duration = */ Toast.LENGTH_LONG
            ).show()
        }
    }
}

fun Context.showMediationDebuggerApplovin() {
    if (BuildConfig.DEBUG) {
        AppLovinSdk.getInstance(this).showMediationDebugger()
    } else {
        Toast.makeText(
            /* context = */ this,
            /* text = */ "This feature is only available in debug mode",
            /* duration = */ Toast.LENGTH_LONG
        ).show()
    }
}

fun Activity.createAdBanner(
    logTag: String?,
    bkgColor: Int = Color.RED,
    viewGroup: ViewGroup?,
    isAdaptiveBanner: Boolean,
): MaxAdView {
    val log = "$logTag - createAdBanner"
    val enableAdBanner = this.getString(R.string.EnableAdBanner) == "true"
    var id = "1234567890123456" // dummy id
    if (enableAdBanner) {
        id = this.getString(R.string.BANNER)
//        viewGroup?.isVisible = true
    } else {
//        viewGroup?.isVisible = false
    }
    i(log, "enableAdBanner $enableAdBanner -> $id")
    val adView = MaxAdView(id, this)
    adView.let { ad ->
        ad.setListener(object : MaxAdViewAdListener {
            override fun onAdLoaded(p0: MaxAd?) {
                i(log, "onAdLoaded")
//                viewGroup?.isVisible = true
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
                viewGroup?.isVisible = false
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
            val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(this).height
            val heightPx = AppLovinSdkUtils.dpToPx(this, heightDp)

            ad.layoutParams = FrameLayout.LayoutParams(width, heightPx)
            ad.setExtraParameter("adaptive_banner", "true")
            ad.setLocalExtraParameter("adaptive_banner_width", 400)
            ad.adFormat.getAdaptiveSize(400, this).height // Set your ad height to this value
        } else {
            val isTablet = AppLovinSdkUtils.isTablet(this)
            val heightPx = AppLovinSdkUtils.dpToPx(this, if (isTablet) 90 else 50)

            ad.layoutParams = FrameLayout.LayoutParams(
                /* width = */ ViewGroup.LayoutParams.MATCH_PARENT,
                /* height = */ heightPx
            )
        }


        if (enableAdBanner) {
            ad.setBackgroundColor(bkgColor)
        } else {
            ad.setBackgroundColor(getColor(com.loitp.R.color.red))
        }
        viewGroup?.addView(adView)
        ad.loadAd()
    }
    return adView
}

fun ViewGroup.destroyAdBanner(adView: MaxAdView?) {
    adView?.destroy()
    this.removeAllViews()
}
