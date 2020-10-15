package com.core.helper.mup.comic.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LActivityUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.interfaces.Callback2
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_splash.*

@LogTag("ComicSplashActivity")
@IsFullScreen(false)
class ComicSplashActivity : BaseFontActivity() {

    private var adView: AdView? = null
    private var admobBannerUnitId: String? = null
    private var isShowDialogCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_comic_splash)

        admobBannerUnitId = intent.getStringExtra(Constants.COMIC_ADMOB_ID_BANNER)

        admobBannerUnitId?.let { id ->
            LSharedPrefsUtil.instance.putString(Constants.COMIC_ADMOB_ID_BANNER, id)
        }

        intent.getBooleanExtra(Constants.COMIC_SHOW_DONATION, false).let { isShowDonation ->
            LSharedPrefsUtil.instance.putBoolean(Constants.COMIC_SHOW_DONATION, isShowDonation)
        }

        if (admobBannerUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.adSize = AdSize.BANNER
                it.adUnitId = admobBannerUnitId
                LUIUtil.createAdBanner(it)
                lnAdView.addView(it)
            }
        }
    }

    private fun goToHome() {
        LUIUtil.setDelay(mls = 2000, runnable = Runnable {
            val intent = Intent(this, ComicActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
            finish()
        })
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
                            logD("onPermissionsChecked do you work now")
                            goToHome()
                        } else {
                            logD("!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            logD("onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        logD("onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(context = this,
                title = getString(R.string.need_permissions),
                msg = getString(R.string.need_permission_msg),
                button1 = getString(R.string.ok),
                button2 = getString(R.string.cancel),
                callback2 = object : Callback2 {
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
        val alertDialog = LDialogUtil.showDialog2(context = this,
                title = getString(R.string.need_permissions),
                msg = getString(R.string.need_permission_msg_setting),
                button1 = getString(R.string.go_to_settings),
                button2 = getString(R.string.cancel),
                callback2 = object : Callback2 {
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
