package vn.loitp.app.activity.ads.admobinterstitial

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_admob_interstitial.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_admob_interstitial)
@LogTag("AdmobInterstitialActivity")
@IsFullScreen(false)
class AdmobInterstitialActivity : BaseFontActivity() {
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        interstitialAd = LUIUtil.createAdFull(this)
        val s = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.ad_full)
        textView.text = s
    }

    override fun onBackPressed() {
        LUIUtil.displayInterstitial(interstitialAd)
        super.onBackPressed()
    }
}
