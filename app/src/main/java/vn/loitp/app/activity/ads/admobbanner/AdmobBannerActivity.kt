package vn.loitp.app.activity.ads.admobbanner

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.activity_admob_banner.*
import vn.loitp.app.R

class AdmobBannerActivity : BaseFontActivity() {
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isShowAdWhenExit = false

        adView = findViewById(R.id.adView)
        LUIUtil.createAdBanner(adView!!)

        val poem = LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp)
        textView.text = poem
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_admob_banner
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
