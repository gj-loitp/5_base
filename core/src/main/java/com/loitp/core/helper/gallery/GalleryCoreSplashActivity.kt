package com.loitp.core.helper.gallery

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.loitpcore.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.helper.gallery.album.GalleryCoreAlbumActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.restApi.restClient.RestClient
import com.loitpcore.utils.util.AppUtils
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_splash.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@SuppressLint("CustomSplashScreen")
@LogTag("GalleryCoreSplashActivity")
@IsFullScreen(false)
class GalleryCoreSplashActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        checkPermission()
    }

    private fun setupViews() {

//        setTransparentStatusNavigationBar()
        RestClient.init(getString(R.string.flickr_URL))

        var urlCoverSplashScreen: String? = intent.getStringExtra(Constants.BKG_SPLASH_SCREEN)
        if (urlCoverSplashScreen.isNullOrEmpty()) {
            urlCoverSplashScreen = Constants.URL_IMG_2
        }
        LImageUtil.load(context = this, any = urlCoverSplashScreen, imageView = ivBkg)
        LUIUtil.setTextShadow(textView = tvCopyright, color = null)
        tvName.text = AppUtils.appName
        LUIUtil.setTextShadow(textView = tvName, color = null)
    }

    private fun goToHome() {
        val removeAlbumList = intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)
        LUIUtil.setDelay(mls = 2000, runnable = {
            val intent = Intent(this, GalleryCoreAlbumActivity::class.java)
            intent.putStringArrayListExtra(
                Constants.KEY_REMOVE_ALBUM_FLICKR_LIST,
                removeAlbumList
                    ?: ArrayList()
            )
            startActivity(intent)
            LActivityUtil.tranIn(this)
            finish()//correct
        })
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
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
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
                    finish()//correct
                    LActivityUtil.tranOut(this)
                }
            }
    }
}
