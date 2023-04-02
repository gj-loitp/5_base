package com.loitp.core.helper.gallery

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.loitp.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.BKG_SPLASH_SCREEN
import com.loitp.core.common.KEY_REMOVE_ALBUM_FLICKR_LIST
import com.loitp.core.common.URL_IMG_2
import com.loitp.core.ext.*
import com.loitp.core.helper.gallery.album.GalleryCoreAlbumActivity
import com.loitp.core.utils.AppUtils
import com.loitp.restApi.restClient.RestClient
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.l_a_flickr_gallery_core_splash.*

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
class GalleryCoreSplashActivity : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_a_flickr_gallery_core_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isValidPackageName()

        setupViews()
        checkPermission()
    }

    private fun setupViews() {

//        setTransparentStatusNavigationBar()
        RestClient.init(getString(R.string.flickr_URL))

        var urlCoverSplashScreen: String? = intent.getStringExtra(BKG_SPLASH_SCREEN)
        if (urlCoverSplashScreen.isNullOrEmpty()) {
            urlCoverSplashScreen = URL_IMG_2
        }
        ivBkg.loadGlide(any = urlCoverSplashScreen)
        tvCopyright.setTextShadow(color = null)
        tvName.text = AppUtils.appName
        tvName.setTextShadow(color = null)
    }

    private fun goToHome() {
        val removeAlbumList = intent.getStringArrayListExtra(KEY_REMOVE_ALBUM_FLICKR_LIST)
        setDelay(mls = 2000, runnable = {
            val intent = Intent(this, GalleryCoreAlbumActivity::class.java)
            intent.putStringArrayListExtra(
                KEY_REMOVE_ALBUM_FLICKR_LIST,
                removeAlbumList
                    ?: ArrayList()
            )
            startActivity(intent)
            this.tranIn()
            finish()//correct
        })
    }

    private fun checkPermission() {
        val color = if (isDarkTheme()) {
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
                    this.tranOut()
                }
            }
    }
}
