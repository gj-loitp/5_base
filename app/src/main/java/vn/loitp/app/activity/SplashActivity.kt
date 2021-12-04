package vn.loitp.app.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.* // ktlint-disable no-wildcard-imports
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.model.App
import com.model.GG
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.* // ktlint-disable no-wildcard-imports
import vn.loitp.app.BuildConfig
import vn.loitp.app.R
import java.io.IOException

@LogTag("SplashActivity")
@IsFullScreen(false)
class SplashActivity : BaseFontActivity() {

    companion object {
        const val LINK_GG_DRIVE_CHECK_READY =
            "https://drive.google.com/uc?export=download&id=1LHnBs4LG1EORS3FtdXpTVwQW2xONvtHo"
    }

    private var isAnimDone = false
    private var isCheckReadyDone = false
    private var isShowDialogCheck = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_splash
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LUIUtil.setDelay(mls = 2500, runnable = {
            isAnimDone = true
            goToHome()
        })
        textViewVersion.text = "Version ${BuildConfig.VERSION_NAME}"

        LUIUtil.setTextShadow(textView = tvPolicy, color = null)
        tvPolicy.setOnClickListener {
            LSocialUtil.openBrowserPolicy(context = this)
        }

        getSettingFromGGDrive()
        getGG()

        startIdleTimeHandler(10 * 1000)
        // val getAddressLog = DebugDB.getAddressLog()
    }

    override fun onActivityUserIdleAfterTime(delayMlsIdleTime: Long, isIdleTime: Boolean) {
        super.onActivityUserIdleAfterTime(delayMlsIdleTime, isIdleTime)
        showShortInformation("onActivityUserIdleAfterTime delayMlsIdleTime $delayMlsIdleTime, isIdleTime: $isIdleTime")
    }

    override fun onResume() {
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    private fun checkPermission() {

        fun checkPermission() {
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
                            val isNeedCheckReady = false
                            if (isNeedCheckReady) {
                                checkReady()
                            } else {
                                isCheckReadyDone = true
                                goToHome()
                            }
                        } else {
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
        }

        isShowDialogCheck = true
        val isCanWriteSystem = LScreenUtil.checkSystemWritePermission()
        if (isCanWriteSystem) {
            checkPermission()
        } else {
            val alertDialog = LDialogUtil.showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to allow modifying system settings",
                button1 = getString(R.string.ok),
                button2 = getString(R.string.cancel),
                onClickButton1 = {
                    isShowDialogCheck = false
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                        intent.data = Uri.parse("package:$packageName")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        LActivityUtil.tranIn(this@SplashActivity)
                    } else {
                        checkPermission()
                    }
                },
                onClickButton2 = {
                    onBackPressed()
                }
            )
            alertDialog.setCancelable(false)
        }
    }

    private fun goToHome() {
        logD("goToHome isAnimDone $isAnimDone, isCheckReadyDone $isCheckReadyDone")

        if (isAnimDone && isCheckReadyDone) {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
            finish()
        }
    }

    private fun showDialogNotReady() {
        runOnUiThread {
            val title = if (LConnectivityUtil.isConnected()) {
                "This app is not available now"
            } else {
                getString(R.string.check_ur_connection)
            }
            val alertDial = LDialogUtil.showDialog1(
                context = this,
                title = "Warning",
                msg = title,
                button1 = "Ok",
                onClickButton1 = {
                    onBackPressed()
                }
            )
            alertDial.setCancelable(false)
        }
    }

    private fun checkReady() {
        if (LPrefUtil.getCheckAppReady()) {
            isCheckReadyDone = true
            goToHome()
            return
        }
        val request = Request.Builder().url(url = LINK_GG_DRIVE_CHECK_READY).build()
        val okHttpClient = OkHttpClient()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                logE("onFailure $e")
                showDialogNotReady()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful && response.body != null) {
                    val versionServer = Integer.parseInt(response.body!!.string())
                    logD("onResponse $versionServer")
                    if (versionServer == 1) {
                        isCheckReadyDone = true
                        LPrefUtil.setCheckAppReady(true)
                        goToHome()
                    } else {
                        showDialogNotReady()
                    }
                } else {
                    logD("onResponse !isSuccessful: $response")
                    showDialogNotReady()
                }
            }
        })
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(
            context = this,
            title = "Need Permissions",
            msg = "This app needs permission to use this feature. You can grant them in app settings.",
            button1 = "GOTO SETTINGS",
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

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(
            context = this,
            title = "Need Permissions",
            msg = "This app needs permission to use this feature.",
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

    private fun getSettingFromGGDrive() {
        val linkGGDriveConfigSetting =
            "https://drive.google.com/uc?export=download&id=1xqNJBQMzCPzAiAcm673B6ErRRRANCmQT"
        LStoreUtil.getSettingFromGGDrive(
            linkGGDriveSetting = linkGGDriveConfigSetting,
            onGGFailure = { _: Call, _: IOException ->
            },
            onGGResponse = { app: App?, isNeedToShowMsg: Boolean ->
                logD(
                    "getSettingFromGGDrive setting " + isNeedToShowMsg + " -> " + BaseApplication.gson.toJson(
                        app
                    )
                )
            }
        )
    }

    private fun getGG() {
        val linkGGDrive =
            "https://drive.google.com/uc?export=download&id=1femuL17MUTz7t0yqUkMWB5yCea1W6kqI"
        LStoreUtil.getTextFromGGDrive(
            linkGGDrive = linkGGDrive,
            onGGFailure = { _: Call, e: Exception ->
                e.printStackTrace()
            },
            onGGResponse = { listGG: ArrayList<GG> ->
                logD("getGG listGG: -> " + BaseApplication.gson.toJson(listGG))
            }
        )
    }
}
