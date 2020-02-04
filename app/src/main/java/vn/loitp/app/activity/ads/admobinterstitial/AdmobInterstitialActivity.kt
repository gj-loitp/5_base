package vn.loitp.app.activity.ads.admobinterstitial

import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.InterstitialAd
import vn.loitp.app.R

class AdmobInterstitialActivity : BaseFontActivity() {
    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false//remove show duplicate ads in BaseActivity
        interstitialAd = LUIUtil.createAdFull(activity)
        val tv = findViewById<TextView>(R.id.tv)
        val s = LStoreUtil.readTxtFromRawFolder(activity, R.raw.ad_full)
        tv.text = s
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_admob_interstitial
    }

    override fun onBackPressed() {
        LUIUtil.displayInterstitial(interstitialAd!!)
        super.onBackPressed()
    }
}
