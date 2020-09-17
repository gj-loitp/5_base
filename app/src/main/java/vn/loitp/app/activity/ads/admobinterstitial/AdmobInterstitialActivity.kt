package vn.loitp.app.activity.ads.admobinterstitial

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_admob_interstitial.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_admob_interstitial)
class AdmobInterstitialActivity : BaseFontActivity() {
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false//remove show duplicate ads in BaseActivity
        interstitialAd = LUIUtil.createAdFull(activity)
        val s = LStoreUtil.readTxtFromRawFolder(activity, R.raw.ad_full)
        textView.text = s
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun onBackPressed() {
        LUIUtil.displayInterstitial(interstitialAd)
        super.onBackPressed()
    }
}
