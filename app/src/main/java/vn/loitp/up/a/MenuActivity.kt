package vn.loitp.up.a

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
import com.loitp.core.common.*
import com.loitp.core.ext.*
import com.loitp.core.helper.adHelper.AdHelperActivity
import vn.loitp.R
import vn.loitp.a.api.MenuAPIActivity
import vn.loitp.a.cv.MenuCustomViewsActivity
import vn.loitp.a.db.MenuDatabaseActivity
import vn.loitp.a.demo.MenuDemoActivity
import vn.loitp.a.func.MenuFunctionActivity
import vn.loitp.a.game.MenuGameActivity
import vn.loitp.a.interviewVN.InterviewVNIQActivity
import vn.loitp.a.more.MoreActivity
import vn.loitp.a.network.NetworkActivity
import vn.loitp.a.pattern.MenuPatternActivity
import vn.loitp.a.picker.MenuPickerActivity
import vn.loitp.a.sec.MenuSecurityActivity
import vn.loitp.a.sv.MenuServiceActivity
import vn.loitp.a.tut.MenuTutorialActivity
import vn.loitp.databinding.AMenuBinding
import vn.loitp.up.a.anim.MenuAnimationActivity
import vn.loitp.up.a.u.UtilsActivity
import vn.loitp.up.a.u.UtilsCoreActivity

@LogTag("MenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
@IsKeepScreenOn(true)
class MenuActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupConfigGoogle()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuActivity::class.java.simpleName
        }
        binding.tvPolicy.apply {
            this.setTextUnderline()
            setSafeOnClickListener {
                this@MenuActivity.openUrlInBrowser(
                    url = URL_POLICY_NOTION
                )
            }
        }
        binding.swDarkTheme.apply {
            isChecked = isDarkTheme()
            setOnCheckedChangeListener { _, isDarkTheme ->
                if (isDarkTheme) {
                    setDarkTheme(isDarkTheme = true)
                } else {
                    setDarkTheme(isDarkTheme = false)
                }
                finish()//correct
                startActivity(Intent(this@MenuActivity, MenuActivity::class.java))
                overridePendingTransition(0, 0)
            }
        }
        binding.btApi.setOnClickListener(this)
        binding.btAnimation.setOnClickListener(this)
        binding.btCustomView.setOnClickListener(this)
        binding.btDemo.setOnClickListener(this)
        binding.btFunction.setOnClickListener(this)
        binding.btRateApp.setOnClickListener(this)
        binding.btMoreApp.setOnClickListener(this)
        binding.btDatabase.setOnClickListener(this)
        binding.btPattern.setOnClickListener(this)
        binding.btChat.setOnClickListener(this)
        binding.btGithub.setOnClickListener(this)
        binding.btAdHelper.setOnClickListener(this)
        binding.btFbFanpage.setOnClickListener(this)
        binding.btFrmMore.setOnClickListener(this)
        binding.btTutorial.setOnClickListener(this)
        binding.btPicker.setOnClickListener(this)
        binding.btNetwork.setOnClickListener(this)
        binding.btSecurity.setOnClickListener(this)
        binding.btService.setOnClickListener(this)
        binding.btUtils.setOnClickListener(this)
        binding.btUtilsCore.setOnClickListener(this)
        binding.btGame.setOnClickListener(this)
        binding.btFeedback.setOnClickListener(this)
        binding.btInterviewVNIQActivity.setOnClickListener(this)
        binding.tvMoreApp.setOnClickListener(this)
    }

    private fun setupConfigGoogle() {
        val app = getGGAppSetting()
        val isFullData = app.config?.isFullData == true
        if (isFullData) {
            binding.btApi.isVisible = true
            binding.btAnimation.isVisible = true
            binding.btCustomView.isVisible = true
            binding.btDemo.isVisible = true
            binding.btFunction.isVisible = true
            binding.btRateApp.isVisible = true
            binding.btMoreApp.isVisible = true
            binding.btDatabase.isVisible = true
            binding.btPattern.isVisible = true
            binding.btChat.isVisible = true
            binding.btGithub.isVisible = true
            binding.btAdHelper.isVisible = true
            binding.btFbFanpage.isVisible = true
            binding.btFrmMore.isVisible = true
            binding.btTutorial.isVisible = true
            binding.btPicker.isVisible = true
            binding.btNetwork.isVisible = true
            binding.btSecurity.isVisible = true
            binding.btService.isVisible = true
            binding.btUtils.isVisible = true
        } else {
            binding.btApi.isVisible = true
            binding.btAnimation.isVisible = false
            binding.btCustomView.isVisible = false
            binding.btDemo.isVisible = false
            binding.btFunction.isVisible = false
            binding.btRateApp.isVisible = true
            binding.btMoreApp.isVisible = true
            binding.btDatabase.isVisible = false
            binding.btPattern.isVisible = false
            binding.btChat.isVisible = false
            binding.btGithub.isVisible = false
            binding.btAdHelper.isVisible = false
            binding.btFbFanpage.isVisible = false
            binding.btFrmMore.isVisible = false
            binding.btTutorial.isVisible = false
            binding.btPicker.isVisible = false
            binding.btNetwork.isVisible = true
            binding.btSecurity.isVisible = false
            binding.btService.isVisible = false
            binding.btUtils.isVisible = false
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
            binding.btApi -> launchActivity(MenuAPIActivity::class.java)
            binding.btAnimation -> launchActivity(MenuAnimationActivity::class.java)
            binding.btCustomView -> launchActivity(MenuCustomViewsActivity::class.java)
            binding.btDemo -> launchActivity(MenuDemoActivity::class.java)
            binding.btRateApp -> this.rateApp(packageName)
            binding.btMoreApp -> this.moreApp()
            binding.btFunction -> launchActivity(MenuFunctionActivity::class.java)
            binding.btGame -> launchActivity(MenuGameActivity::class.java)
            binding.btDatabase -> launchActivity(MenuDatabaseActivity::class.java)
            binding.btPattern -> launchActivity(MenuPatternActivity::class.java)
            binding.btChat -> this.chatMessenger()
            binding.btGithub -> {
                this.openUrlInBrowser(
                    url = "https://github.com/tplloi/base"
                )
            }
            binding.btAdHelper -> {
                launchActivity(cls = AdHelperActivity::class.java, data = {
                    it.putExtra(AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                    it.putExtra(AD_HELPER_COLOR_PRIMARY, Color.RED)
                    it.putExtra(AD_HELPER_COLOR_BACKGROUND, Color.YELLOW)
                    it.putExtra(AD_HELPER_COLOR_STATUS_BAR, Color.GREEN)
                    it.putExtra(AD_HELPER_IS_LIGHT_ICON_STATUS_BAR, true)
                })
            }
            binding.btFbFanpage -> this.likeFacebookFanpage()
            binding.btFrmMore -> launchActivity(MoreActivity::class.java)
            binding.btTutorial -> launchActivity(MenuTutorialActivity::class.java)
            binding.btPicker -> launchActivity(MenuPickerActivity::class.java)
            binding.btNetwork -> launchActivity(NetworkActivity::class.java)
            binding.btSecurity -> launchActivity(MenuSecurityActivity::class.java)
            binding.btService -> launchActivity(MenuServiceActivity::class.java)
            binding.btUtils -> launchActivity(UtilsActivity::class.java)
            binding.btUtilsCore -> launchActivity(UtilsCoreActivity::class.java)
            binding.btFeedback -> {
                this.sendEmail(
                    to = "roy93group@gmail.com",
                    cc = "roy93group@gmail.com",
                    bcc = "roy93group@gmail.com",
                    subject = "Feedback",
                    body = "..."
                )
            }
            binding.btInterviewVNIQActivity -> launchActivity(InterviewVNIQActivity::class.java)
            binding.tvMoreApp -> this.moreApp()
        }
    }
}
