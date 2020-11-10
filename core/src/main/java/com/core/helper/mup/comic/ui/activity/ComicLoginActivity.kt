package com.core.helper.mup.comic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.mup.comic.service.ComicApiClient
import com.core.helper.mup.comic.viewmodel.ComicLoginViewModel
import com.core.utilities.LActivityUtil
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.core.utilities.LValidateUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.l_activity_comic_login.*

@LogTag("ComicLoginActivity")
@IsFullScreen(false)
class ComicLoginActivity : BaseFontActivity() {

    private var comicLoginViewModel: ComicLoginViewModel? = null
    private var adView: AdView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_comic_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()

        //hard code login
        comicLoginViewModel?.login(
                email = "appmb@truyentranhvn.org",
                password = "appmb@truyentranhvn.org"
        )

        LValidateUtil.isValidCoreComicMup()
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
            vm.loginActionLiveData.observe(this, { actionData ->
                logD("<<<loginActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess
                if (isDoing == true) {
                    indicatorView.smoothToShow()
                } else {
                    indicatorView.smoothToHide()

                    if (isSuccess == true) {
                        val data = actionData.data
//                    logD("<<<loginActionLiveData observe " + BaseApplication.gson.toJson(data))
                        val token = data?.jwtToken
//                    logD("token $token")
                        if (token.isNullOrEmpty()) {
                            showDialogError(errMsg = getString(R.string.err_unknow), runnable = {
                                onBackPressed()
                            })
                        } else {

                            //save comic token
                            LSharedPrefsUtil.instance.putString(Constants.COMIC_TOKEN, token)
                            ComicApiClient.addAuthorization(token)

                            val intent = Intent(this, ComicActivity::class.java)
                            startActivity(intent)
                            LActivityUtil.tranIn(context = this)
                            finish()
                        }
                    } else {
                        val error = actionData.errorResponse
                        showDialogError(errMsg = error?.message, runnable = {
                            onBackPressed()
                        })
                    }
                }
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
