package vn.loitp.app.activity.ads.admobinterstitial

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LStoreUtil
import com.loitpcore.core.utilities.LUIUtil
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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = AdMobInterstitialActivity::class.java.simpleName
        }
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
