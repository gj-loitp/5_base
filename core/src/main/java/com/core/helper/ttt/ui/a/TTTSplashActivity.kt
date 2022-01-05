package com.core.helper.ttt.ui.a

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
import com.core.utilities.*
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.l_activity_ttt_comic_splash.*

@LogTag("TTTSplashActivity")
@IsFullScreen(false)
class TTTSplashActivity : BaseFontActivity() {

    private var adView: AdView? = null
    private var admobBannerUnitId: String? = null
    private var isShowDialogCheck: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_ttt_comic_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        admobBannerUnitId = intent.getStringExtra(Constants.COMIC_ADMOB_ID_BANNER)

        admobBannerUnitId?.let { id ->
            LSharedPrefsUtil.instance.putString(Constants.COMIC_ADMOB_ID_BANNER, id)
        }

        if (admobBannerUnitId.isNullOrEmpty()) {
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
        LValidateUtil.isValidPackageName()
    }

    private fun goToHome() {
        LUIUtil.setDelay(mls = 1500, runnable = {
            val intent = Intent(this, TTTComicLoginActivity::class.java)
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

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
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

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    logD("onPermissionRationaleShouldBeShown")
                    token.continuePermissionRequest()
                }
            })
            .onSameThread()
            .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(
            context = this,
            title = getString(R.string.need_permissions),
            msg = getString(R.string.need_permission_msg),
            button1 = getString(R.string.ok),
            button2 = getString(R.string.cancel),
            onClickButton1 = {
                checkPermission()
            },
            onClickButton2 = {
                onBackPressed()
            }
        )
        alertDialog.setCancelable(false)
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(
            context = this,
            title = getString(R.string.need_permissions),
            msg = getString(R.string.need_permission_msg_setting),
            button1 = getString(R.string.go_to_settings),
            button2 = getString(R.string.cancel),
            onClickButton1 = {
                isShowDialogCheck = false
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, 101)
            },
            onClickButton2 = {
                onBackPressed()
            }
        )
        alertDialog.setCancelable(false)
    }
}
