package vn.loitp.app.activity.ads.admobinterstitial

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.interstitial.InterstitialAd
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
        LUIUtil.createAdFull(
            context = this,
            onAdLoaded = {
                interstitialAd = it
            },
            onAdFailedToLoad = {
                logE("createAdFull onAdFailedToLoad ${it.message}")
            }
        )
        val s = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.ad_full)
        textView.text = s
    }

    override fun onBackPressed() {
        LUIUtil.displayInterstitial(activity = this, interstitial = interstitialAd)
        super.onBackPressed()
    }
}
