package vn.loitp.up.a.demo.ad

import android.content.Context
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdk

object Applovin {
    fun setupAd(c: Context) {
        // Initialize the AppLovin SDK
        AppLovinSdk.getInstance(c).mediationProvider = AppLovinMediationProvider.MAX
        AppLovinSdk.getInstance(c).initializeSdk {
            // AppLovin SDK is initialized, start loading ads now or later if ad gate is reached

        }
    }
}
