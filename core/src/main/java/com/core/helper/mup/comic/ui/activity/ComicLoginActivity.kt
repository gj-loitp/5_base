package com.core.helper.mup.comic.ui.activity

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_splash.*

@LogTag("ComicSplashActivity")
@IsFullScreen(false)
class ComicLoginActivity : BaseFontActivity() {

    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_comic_login)

        setupViews()
    }

    private fun setupViews() {
        val admobBannerUnitId = LSharedPrefsUtil.instance.getString(Constants.COMIC_ADMOB_ID_BANNER)
        if (admobBannerUnitId.isEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.adSize = AdSize.SMART_BANNER
                it.adUnitId = admobBannerUnitId
                LUIUtil.createAdBanner(it)
                lnAdView.addView(it)
            }
        }
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
