package vn.loitp.a

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsKeepScreenOn
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import com.loitp.core.utilities.LPrefUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu.*
import vn.loitp.R
import vn.loitp.a.anim.MenuAnimationActivityFont
import vn.loitp.a.api.MenuAPIActivityFont
import vn.loitp.a.cv.MenuCustomViewsActivityFont
import vn.loitp.a.db.MenuDatabaseActivityFont
import vn.loitp.a.demo.MenuDemoActivityFont
import vn.loitp.a.func.MenuFunctionActivityFont
import vn.loitp.a.game.MenuGameActivityFont
import vn.loitp.a.interviewVN.InterviewVNIQActivityFont
import vn.loitp.a.more.MoreActivityFont
import vn.loitp.a.network.NetworkActivityFont
import vn.loitp.a.pattern.MenuPatternActivityFont
import vn.loitp.a.picker.MenuPickerActivityFont
import vn.loitp.a.sec.MenuSecurityActivityFont
import vn.loitp.a.sv.MenuServiceActivityFont
import vn.loitp.a.tut.MenuTutorialActivityFont
import vn.loitp.a.u.UtilsActivity
import vn.loitp.a.u.UtilsCoreActivity

@LogTag("MenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
@IsKeepScreenOn(true)
class MenuActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupConfigGoogle()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuActivityFont::class.java.simpleName
        }

        tvPolicy.apply {
            LUIUtil.setTextUnderline(this)
            setSafeOnClickListener {
                this@MenuActivityFont.openUrlInBrowser(
                    url = Constants.URL_POLICY
                )
            }
        }

        swDarkTheme.apply {
            isChecked = LUIUtil.isDarkTheme()
            setOnCheckedChangeListener { _, isDarkTheme ->
                if (isDarkTheme) {
                    LUIUtil.setDarkTheme(isDarkTheme = true)
                } else {
                    LUIUtil.setDarkTheme(isDarkTheme = false)
                }
                finish()//correct
                startActivity(Intent(this@MenuActivityFont, MenuActivityFont::class.java))
                overridePendingTransition(0, 0)
            }
        }

        btApi.setOnClickListener(this)
        btAnimation.setOnClickListener(this)
        btCustomView.setOnClickListener(this)
        btDemo.setOnClickListener(this)
        btFunction.setOnClickListener(this)
        btRateApp.setOnClickListener(this)
        btMoreApp.setOnClickListener(this)
        btDatabase.setOnClickListener(this)
        btPattern.setOnClickListener(this)
        btChat.setOnClickListener(this)
        btGithub.setOnClickListener(this)
        btAdHelper.setOnClickListener(this)
        btFbFanpage.setOnClickListener(this)
        btFrmMore.setOnClickListener(this)
        btTutorial.setOnClickListener(this)
        btPicker.setOnClickListener(this)
        btNetwork.setOnClickListener(this)
        btSecurity.setOnClickListener(this)
        btService.setOnClickListener(this)
        btUtils.setOnClickListener(this)
        btUtilsCore.setOnClickListener(this)
        btGame.setOnClickListener(this)
        btFeedback.setOnClickListener(this)
        btInterviewVNIQActivity.setOnClickListener(this)
    }

    private fun setupConfigGoogle() {
        val app = LPrefUtil.getGGAppSetting()
//        logD(">>>setupConfigGoogle " + BaseApplication.gson.toJson(app))
        val isFullData = app.config?.isFullData == true
//        logD(">>>setupConfigGoogle isFullData $isFullData")
        if (isFullData) {
            btApi.isVisible = true
            btAnimation.isVisible = true
            btCustomView.isVisible = true
            btDemo.isVisible = true
            btFunction.isVisible = true
            btRateApp.isVisible = true
            btMoreApp.isVisible = true
            btDatabase.isVisible = true
            btPattern.isVisible = true
            btChat.isVisible = true
            btGithub.isVisible = true
            btAdHelper.isVisible = true
            btFbFanpage.isVisible = true
            btFrmMore.isVisible = true
            btTutorial.isVisible = true
            btPicker.isVisible = true
            btNetwork.isVisible = true
            btSecurity.isVisible = true
            btService.isVisible = true
            btUtils.isVisible = true
        } else {
            btApi.isVisible = true
            btAnimation.isVisible = false
            btCustomView.isVisible = false
            btDemo.isVisible = false
            btFunction.isVisible = false
            btRateApp.isVisible = true
            btMoreApp.isVisible = true
            btDatabase.isVisible = false
            btPattern.isVisible = false
            btChat.isVisible = false
            btGithub.isVisible = false
            btAdHelper.isVisible = false
            btFbFanpage.isVisible = false
            btFrmMore.isVisible = false
            btTutorial.isVisible = false
            btPicker.isVisible = false
            btNetwork.isVisible = true
            btSecurity.isVisible = false
            btService.isVisible = false
            btUtils.isVisible = false
        }
    }

    private var doubleBackToExitPressedOnce = false

    override fun onBaseBackPressed() {
//        super.onBaseBackPressed()
        if (doubleBackToExitPressedOnce) {
//            onBaseBackPressed()
            super.onBaseBackPressed()//correct
            return
        }
        this.doubleBackToExitPressedOnce = true
        showSnackBarInfor(msg = getString(R.string.press_again_to_exit), isFullWidth = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onClick(v: View) {
        when (v) {
            btApi -> launchActivity(MenuAPIActivityFont::class.java)
            btAnimation -> launchActivity(MenuAnimationActivityFont::class.java)
            btCustomView -> launchActivity(MenuCustomViewsActivityFont::class.java)
            btDemo -> launchActivity(MenuDemoActivityFont::class.java)
            btRateApp -> this.rateApp(packageName)
            btMoreApp -> this.moreApp()
            btFunction -> launchActivity(MenuFunctionActivityFont::class.java)
            btGame -> launchActivity(MenuGameActivityFont::class.java)
            btDatabase -> launchActivity(MenuDatabaseActivityFont::class.java)
            btPattern -> launchActivity(MenuPatternActivityFont::class.java)
            btChat -> this.chatMessenger()
            btGithub -> {
                this.openUrlInBrowser(
                    url = "https://github.com/tplloi/base"
                )
            }
            btAdHelper -> {
                launchActivity(cls = AdHelperActivity::class.java, data = {
                    it.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                    it.putExtra(Constants.AD_HELPER_COLOR_PRIMARY, Color.RED)
                    it.putExtra(Constants.AD_HELPER_COLOR_BACKGROUND, Color.YELLOW)
                    it.putExtra(Constants.AD_HELPER_COLOR_STATUS_BAR, Color.GREEN)
                    it.putExtra(Constants.AD_HELPER_IS_LIGHT_ICON_STATUS_BAR, true)
                })
            }
            btFbFanpage -> this.likeFacebookFanpage()
            btFrmMore -> launchActivity(MoreActivityFont::class.java)
            btTutorial -> launchActivity(MenuTutorialActivityFont::class.java)
            btPicker -> launchActivity(MenuPickerActivityFont::class.java)
            btNetwork -> launchActivity(NetworkActivityFont::class.java)
            btSecurity -> launchActivity(MenuSecurityActivityFont::class.java)
            btService -> launchActivity(MenuServiceActivityFont::class.java)
            btUtils -> launchActivity(UtilsActivity::class.java)
            btUtilsCore -> launchActivity(UtilsCoreActivity::class.java)
            btFeedback -> {
                this.sendEmail(
                    to = "roy93group@gmail.com",
                    cc = "roy93group@gmail.com",
                    bcc = "roy93group@gmail.com",
                    subject = "Feedback",
                    body = "..."
                )
            }
            btInterviewVNIQActivity -> launchActivity(InterviewVNIQActivityFont::class.java)
        }
    }
}
