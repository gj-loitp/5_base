package com.core.helper.mup.comic.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.mup.comic.viewmodel.ComicLoginViewModel
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_splash.*

@LogTag("loitppComicSplashActivity")
@IsFullScreen(false)
class ComicLoginActivity : BaseFontActivity() {

    private var comicLoginViewModel: ComicLoginViewModel? = null
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_comic_login)

        setupViews()
        setupViewModels()

        //hard code login
        comicLoginViewModel?.login(
                email = "appmb@truyentranhvn.org",
                password = "appmb@truyentranhvn.org"
        )
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

    private fun setupViewModels() {
        comicLoginViewModel = getViewModel(ComicLoginViewModel::class.java)
        comicLoginViewModel?.let { vm ->
            vm.loginActionLiveData.observe(this, Observer { actionData ->
                logD("<<<loginActionLiveData observe " + BaseApplication.gson.toJson(actionData))

            })
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