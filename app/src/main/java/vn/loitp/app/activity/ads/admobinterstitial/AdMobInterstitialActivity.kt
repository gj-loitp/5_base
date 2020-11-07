package vn.loitp.app.activity.ads.admobinterstitial

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.LoadAdError
import kotlinx.android.synthetic.main.activity_admob_interstitial.*
import vn.loitp.app.R

@LogTag("AdMobInterstitialActivity")
@IsFullScreen(false)
class AdMobInterstitialActivity : BaseFontActivity() {
    private var interstitialAd: InterstitialAd? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_admob_interstitial
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        interstitialAd = LUIUtil.createAdFull(this)
        interstitialAd?.adListener = object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError?) {
                super.onAdFailedToLoad(loadAdError)
                logD("onAdFailedToLoad loadAdError $loadAdError")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                logD("onAdOpened")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                logD("onAdClicked")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                logD("onAdLoaded")
            }
        }
        val s = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.ad_full)
        textView.text = s
    }

    override fun onBackPressed() {
        LUIUtil.displayInterstitial(interstitial = interstitialAd)
        super.onBackPressed()
    }
}
