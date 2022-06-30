package com.loitpcore.core.helper.gallery

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.loitpcore.R
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.gallery.album.GalleryCoreAlbumActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.core.utilities.LValidateUtil
import com.loitpcore.restapi.restclient.RestClient
import com.loitpcore.utils.util.AppUtils
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_splash.*

@SuppressLint("CustomSplashScreen")
@LogTag("GalleryCoreSplashActivity")
@IsFullScreen(false)
class GalleryCoreSplashActivity : BaseFontActivity() {
    private var adView: AdView? = null
    private var adMobBannerUnitId: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setTransparentStatusNavigationBar()
        RestClient.init(getString(R.string.flickr_URL))
        adMobBannerUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
//        logD("admobBannerUnitId $admobBannerUnitId")

        if (adMobBannerUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.setAdSize(AdSize.BANNER)
                adMobBannerUnitId?.let { id ->
                    it.adUnitId = id
                }
                LUIUtil.createAdBanner(it)
                lnAdView.addView(it)
//                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
//                LUIUtil.setMargins(view = it, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = navigationHeight + navigationHeight / 4)
            }
        }

        var urlCoverSplashScreen: String? = intent.getStringExtra(Constants.BKG_SPLASH_SCREEN)
        if (urlCoverSplashScreen.isNullOrEmpty()) {
            urlCoverSplashScreen = Constants.URL_IMG_2
        }
        LImageUtil.load(context = this, any = urlCoverSplashScreen, imageView = ivBkg)
        LUIUtil.setTextShadow(textView = tvCopyright, color = null)
        tvName.text = AppUtils.appName
        LUIUtil.setTextShadow(textView = tvName, color = null)

        LValidateUtil.isValidPackageName()

        checkPermission()
    }

    private fun goToHome() {
        val removeAlbumList = intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)
        LUIUtil.setDelay(mls = 2000, runnable = {
            val intent = Intent(this, GalleryCoreAlbumActivity::class.java)
            intent.putExtra(Constants.AD_UNIT_ID_BANNER, adMobBannerUnitId)
            intent.putStringArrayListExtra(
                Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
                removeAlbumList
                    ?: ArrayList()
            )
            startActivity(intent)
            LActivityUtil.tranIn(this)
            finish()
        })
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

    private fun checkPermission() {
        val color = if (LUIUtil.isDarkTheme()) {
            Color.WHITE
        } else {
            Color.BLACK
        }
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            )
            .setDialogTintColor(color, color)
            .onExplainRequestReason { scope, deniedList, _ ->
                val message = getString(R.string.app_name) + getString(R.string.needs_per)
                scope.showRequestReasonDialog(
                    permissions = deniedList,
                    message = message,
                    positiveText = getString(R.string.allow),
                    negativeText = getString(R.string.deny)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    permissions = deniedList,
                    message = getString(R.string.per_manually_msg),
                    positiveText = getString(R.string.ok),
                    negativeText = getString(R.string.cancel)
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    goToHome()
                } else {
                    finish()
                    LActivityUtil.tranOut(this)
                }
            }
    }
}
