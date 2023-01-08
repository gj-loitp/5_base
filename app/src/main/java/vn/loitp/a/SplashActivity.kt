package vn.loitp.a

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.ext.*
import com.loitp.core.utilities.*
import com.loitp.model.App
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.a_splash.*
import okhttp3.Call
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.a.anim.konfetti.Presets
import java.io.IOException

@SuppressLint("CustomSplashScreen")
@LogTag("SplashActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SplashActivity : BaseActivityFont() {

    private var isAnimDone = false
    private var isCheckReadyDone = false
    private var isShowDialogCheck = false

    override fun setLayoutResourceId(): Int {
        return R.layout.a_splash
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        konfettiView.start(Presets.festive())
        setDelay(mls = 2500, runnable = {
            isAnimDone = true
            goToHome()
        })
        textViewVersion.text = "Version ${BuildConfig.VERSION_NAME}"

        tvPolicy.apply {
            this.setTextUnderline()
            this.setTextShadow(color = null)
            setOnClickListener {
                this@SplashActivity.openBrowserPolicy()
            }
        }

        startIdleTimeHandler(10 * 1000)
        // val getAddressLog = DebugDB.getAddressLog()
    }

    override fun onActivityUserIdleAfterTime(
        delayMlsIdleTime: Long,
        isIdleTime: Boolean
    ) {
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

        fun checkPer() {
            isShowDialogCheck = true
            val color = if (isDarkTheme()) {
                Color.WHITE
            } else {
                Color.BLACK
            }

            val listPer = ArrayList<String>()
            listPer.add(Manifest.permission.ACCESS_FINE_LOCATION)
            listPer.add(Manifest.permission.CAMERA)

            //ACCESS_BACKGROUND_LOCATION publish len store rat kho khan, khong can thiet
            //ban build debug thi chi test de biet feat nay work
            //con ban release thi khong can dau
            //nho uncomment per ACCESS_BACKGROUND_LOCATION trong manifest
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && BuildConfig.DEBUG) {
//                listPer.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//            }

            PermissionX.init(this)
                .permissions(listPer)
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
                        val isNeedCheckReady = true
                        if (isNeedCheckReady) {
                            checkReady()
                        } else {
                            isCheckReadyDone = true
                            goToHome()
                        }
                    } else {
                        finish()//correct
                        this.tranOut()
                    }
                    isShowDialogCheck = false
                }
        }

        val isCanWriteSystem = checkSystemWritePermission()
        if (isCanWriteSystem) {
            checkPer()
        } else {
            val alertDialog = this.showDialog2(
                title = "Need Permissions",
                msg = "This app needs permission to allow modifying system settings",
                button1 = getString(R.string.ok),
                button2 = getString(R.string.cancel),
                onClickButton1 = {
                    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                    intent.data = Uri.parse("package:$packageName")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    this@SplashActivity.tranIn()
                },
                onClickButton2 = {
                    onBaseBackPressed()
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
            this.tranIn()
            finish()
        }
    }

    private fun showDialogNotReady() {
        runOnUiThread {
            val title = if (this.isConnected()) {
                "This app is not available now"
            } else {
                getString(R.string.check_ur_connection)
            }
            val alertDial = this.showDialog1(
                title = "Warning",
                msg = title,
                button1 = "Ok",
                onClickButton1 = {
                    onBaseBackPressed()
                }
            )
            alertDial.setCancelable(false)
        }
    }

    private fun checkReady() {
        if (getCheckAppReady()) {
            val app = getGGAppSetting()
            val isFullData = app.config?.isFullData == true
            if (isFullData) {
                isCheckReadyDone = true
                goToHome()
                return
            } else {
                //continue to download config from drive
            }
        }
        //https://drive.google.com/drive/u/0/folders/1STvbrMp_WSvPrpdm8DYzgekdlwXKsCS9
        val linkGGDriveConfigSetting =
            "https://drive.google.com/uc?export=download&id=16pwq28ZTeP5p1ZeJmgwjHsOofE12XRIf"
        getSettingFromGGDrive(
            linkGGDriveSetting = linkGGDriveConfigSetting,
            onGGFailure = { _: Call, _: IOException ->
                showDialogNotReady()
            },
            onGGResponse = { app: App? ->
                logD(">>>checkReady " + BaseApplication.gson.toJson(app))
                if (app == null || app.config?.isReady == false) {
                    showDialogNotReady()
                } else {
                    setCheckAppReady(true)
                    setGGAppSetting(app)
                    isCheckReadyDone = true
                    goToHome()
                }
            }
        )
    }
}
