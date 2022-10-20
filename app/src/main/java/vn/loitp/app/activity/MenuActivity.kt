package vn.loitp.app.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.adHelper.AdHelperActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LPrefUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.animation.MenuAnimationActivity
import vn.loitp.app.activity.api.MenuAPIActivity
import vn.loitp.app.activity.customviews.MenuCustomViewsActivity
import vn.loitp.app.activity.database.MenuDatabaseActivity
import vn.loitp.app.activity.demo.MenuDemoActivity
import vn.loitp.app.activity.function.MenuFunctionActivity
import vn.loitp.app.activity.game.MenuGameActivity
import vn.loitp.app.activity.more.MoreActivity
import vn.loitp.app.activity.network.NetworkActivity
import vn.loitp.app.activity.pattern.MenuPatternActivity
import vn.loitp.app.activity.picker.MenuPickerActivity
import vn.loitp.app.activity.security.MenuSecurityActivity
import vn.loitp.app.activity.service.MenuServiceActivity
import vn.loitp.app.activity.tutorial.MenuTutorialActivity
import vn.loitp.app.activity.utillsCore.UtilsCoreActivity
import vn.loitp.app.activity.utils.UtilsActivity

@LogTag("MenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupConfigGoogle()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuActivity::class.java.simpleName
        }
        tvPolicy.setSafeOnClickListener {
            LSocialUtil.openUrlInBrowser(context = this, url = Constants.URL_POLICY)
        }

        swDarkTheme.isChecked = LUIUtil.isDarkTheme()
        swDarkTheme.setOnCheckedChangeListener { _, isDarkTheme ->
            if (isDarkTheme) {
                LUIUtil.setDarkTheme(isDarkTheme = true)
            } else {
                LUIUtil.setDarkTheme(isDarkTheme = false)
            }
            finish()//correct
            startActivity(Intent(this, MenuActivity::class.java))
            overridePendingTransition(0, 0)
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
        val intent: Intent? =
            when (v) {
                btApi ->
                    Intent(this, MenuAPIActivity::class.java)
                btAnimation -> Intent(this, MenuAnimationActivity::class.java)
                btCustomView -> Intent(this, MenuCustomViewsActivity::class.java)
                btDemo -> Intent(this, MenuDemoActivity::class.java)
                btRateApp -> {
                    LSocialUtil.rateApp(this, packageName)
                    null
                }
                btMoreApp -> {
                    LSocialUtil.moreApp(this)
                    null
                }
                btFunction -> Intent(this, MenuFunctionActivity::class.java)
                btGame -> Intent(this, MenuGameActivity::class.java)
                btDatabase -> Intent(this, MenuDatabaseActivity::class.java)
                btPattern -> Intent(this, MenuPatternActivity::class.java)
                btChat -> {
                    LSocialUtil.chatMessenger(this)
                    null
                }
                btGithub -> {
                    LSocialUtil.openUrlInBrowser(
                        context = this,
                        url = "https://github.com/tplloi/base"
                    )
                    null
                }
                btAdHelper -> {
                    Intent(this, AdHelperActivity::class.java).apply {
                        putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                    }
                }
                btFbFanpage -> {
                    LSocialUtil.likeFacebookFanpage(this)
                    null
                }
                btFrmMore -> Intent(this, MoreActivity::class.java)
                btTutorial -> Intent(this, MenuTutorialActivity::class.java)
                btPicker -> Intent(this, MenuPickerActivity::class.java)
                btNetwork -> Intent(this, NetworkActivity::class.java)
                btSecurity -> Intent(this, MenuSecurityActivity::class.java)
                btService -> Intent(this, MenuServiceActivity::class.java)
                btUtils -> Intent(this, UtilsActivity::class.java)
                btUtilsCore -> Intent(this, UtilsCoreActivity::class.java)
                else -> null
            }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
