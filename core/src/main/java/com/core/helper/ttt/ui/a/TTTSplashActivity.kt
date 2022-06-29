package com.core.helper.ttt.ui.a

import android.Manifest
import android.content.Intent
import android.graphics.Color
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
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.l_activity_ttt_comic_splash.*

@LogTag("TTTSplashActivity")
@IsFullScreen(false)
class TTTSplashActivity : BaseFontActivity() {

    private var adView: AdView? = null
    private var admobBannerUnitId: String? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_ttt_comic_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        checkPermission()
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
                it.setAdSize(AdSize.BANNER)
                admobBannerUnitId?.let { id ->
                    it.adUnitId = id
                }
                LUIUtil.createAdBanner(it)
                lnAdView.addView(it)
            }
        }
        LValidateUtil.isValidPackageName()
    }

    private fun goToHome() {
        LUIUtil.setDelay(mls = 1500, runnable = {
            val intent = Intent(this, TTTComicLoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
