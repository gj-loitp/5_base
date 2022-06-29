package com.core.helper.ttt.ui.a

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.core.utilities.LValidateUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.l_activity_ttt_comic_login.*

@LogTag("TTTComicLoginActivity")
@IsFullScreen(false)
class TTTComicLoginActivity : BaseFontActivity() {

    private var adView: AdView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_ttt_comic_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        LValidateUtil.isValidPackageName()
        setupViewModels()
    }

    private fun setupViews() {
        val admobBannerUnitId = LSharedPrefsUtil.instance.getString(Constants.COMIC_ADMOB_ID_BANNER)
        if (admobBannerUnitId.isEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.setAdSize(AdSize.BANNER)
                it.adUnitId = admobBannerUnitId
                LUIUtil.createAdBanner(it)
                lnAdView.addView(it)
            }
        }
    }

    private fun setupViewModels() {
        val intent = Intent(this, TTTComicActivity::class.java)
        startActivity(intent)
        LActivityUtil.tranIn(context = this)
        finish()
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
