package com.core.loitp.gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.loitp.gallery.album.GalleryCoreAlbumActivity
import com.core.utilities.*
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.restapi.restclient.RestClient
import com.utils.util.AppUtils
import com.views.layout.floatdraglayout.DisplayUtil
import loitp.core.R
import java.util.*

class GalleryCoreSplashActivity : BaseFontActivity() {
    private var bkgRootView: Int = 0
    private var adView: AdView? = null
    private var admobBannerUnitId: String? = null
    private var isShowDialogCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        setTransparentStatusNavigationBar()
        RestClient.init(getString(R.string.flickr_URL))
        admobBannerUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
        LLog.d(TAG, "admobBannerUnitId $admobBannerUnitId")
        val lnAdview = findViewById<LinearLayout>(R.id.ln_adview)
        if (admobBannerUnitId == null || admobBannerUnitId!!.isEmpty()) {
            lnAdview.visibility = View.GONE
        } else {
            adView = AdView(activity)
            adView?.adSize = AdSize.BANNER
            adView?.adUnitId = admobBannerUnitId
            LUIUtil.createAdBanner(adView!!)
            lnAdview.addView(adView)
            val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4)
        }
        val ivBkg = findViewById<ImageView>(R.id.iv_bkg)
        var urlCoverSplashScreen: String? = intent.getStringExtra(Constants.BKG_SPLASH_SCREEN)
        if (urlCoverSplashScreen == null || urlCoverSplashScreen.isEmpty()) {
            urlCoverSplashScreen = Constants.URL_IMG_2
        }
        LImageUtil.load(activity, urlCoverSplashScreen, ivBkg)
        bkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, Constants.NOT_FOUND)
        LLog.d(TAG, "bkgRootView $bkgRootView")
        if (bkgRootView == Constants.NOT_FOUND) {
            rootView?.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        } else {
            rootView?.setBackgroundResource(bkgRootView)
        }

        val tvCopyright = findViewById<TextView>(R.id.tv_copyright)
        LUIUtil.setTextShadow(tvCopyright)

        val tvName = findViewById<TextView>(R.id.tv_name)
        tvName.text = AppUtils.getAppName()
        LUIUtil.setTextShadow(tvName)
    }

    private fun goToHome() {
        val removeAlbumList = intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)
        LUIUtil.setDelay(3000) {
            val intent = Intent(activity, GalleryCoreAlbumActivity::class.java)
            intent.putExtra(Constants.AD_UNIT_ID_BANNER, admobBannerUnitId)
            intent.putExtra(Constants.BKG_ROOT_VIEW, bkgRootView)
            intent.putStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST, removeAlbumList
                    ?: ArrayList())
            startActivity(intent)
            LActivityUtil.tranIn(activity)
            finish()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return "TAG" + javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_gallery_core_splash
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
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
        isShowDialogCheck = true
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            LLog.d(TAG, "onPermissionsChecked do you work now")
                            goToHome()
                        } else {
                            LLog.d(TAG, "!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            LLog.d(TAG, "onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        LLog.d(TAG, "onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", object : LDialogUtil.Callback2 {
            override fun onClick1() {
                checkPermission()
            }

            override fun onClick2() {
                onBackPressed()
            }
        })
        alertDialog.setCancelable(false)
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(activity, "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", object : LDialogUtil.Callback2 {
            override fun onClick1() {
                isShowDialogCheck = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, 101)
            }

            override fun onClick2() {
                onBackPressed()
            }
        })
        alertDialog.setCancelable(false)
    }
}
