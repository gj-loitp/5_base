package vn.loitp.app.activity.ads.admobbanner

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_admob_banner.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_admob_banner)
@LogTag("AdmobBannerActivity")
@IsFullScreen(false)
class AdmobBannerActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isShowAdWhenExit = false

        LUIUtil.createAdBanner(adView)

        val poem = LStoreUtil.readTxtFromRawFolder(this, R.raw.loitp)
        textView.text = poem
    }

    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView.resume()
        super.onResume()
    }

    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}
