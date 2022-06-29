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
import com.loitpcore.core.helper.adhelper.AdHelperActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.ads.MenuAdsActivity
import vn.loitp.app.activity.animation.MenuAnimationActivity
import vn.loitp.app.activity.api.MenuAPIActivity
import vn.loitp.app.activity.customviews.MenuCustomViewsActivity
import vn.loitp.app.activity.database.MenuDatabaseActivity
import vn.loitp.app.activity.demo.MenuDemoActivity
import vn.loitp.app.activity.function.MenuFunctionActivity
import vn.loitp.app.activity.more.MoreActivity
import vn.loitp.app.activity.network.NetworkActivity
import vn.loitp.app.activity.pattern.MenuPatternActivity
import vn.loitp.app.activity.picker.MenuPickerActivity
import vn.loitp.app.activity.security.SecurityMenuActivity
import vn.loitp.app.activity.service.MenuServiceActivity
import vn.loitp.app.activity.tutorial.MenuTutorialActivity
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
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
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
            finish()
            startActivity(Intent(this, MenuActivity::class.java))
            overridePendingTransition(0, 0)
        }

        btApi.setOnClickListener(this)
        btAnimation.setOnClickListener(this)
        btCustomView.setOnClickListener(this)
        btDemo.setOnClickListener(this)
        btFunction.setOnClickListener(this)
        btAds.setOnClickListener(this)
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
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
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
                btAds -> Intent(this, MenuAdsActivity::class.java)
                btRateApp -> {
                    LSocialUtil.rateApp(this, packageName)
                    null
                }
                btMoreApp -> {
                    LSocialUtil.moreApp(this)
                    null
                }
                btFunction -> Intent(this, MenuFunctionActivity::class.java)
                btDatabase -> Intent(this, MenuDatabaseActivity::class.java)
                btPattern -> Intent(this, MenuPatternActivity::class.java)
                btChat -> {
                    LSocialUtil.chatMessenger(this)
                    null
                }
                btGithub -> {
                    LSocialUtil.openUrlInBrowser(
                        context = this,
                        url = "https://github.com/tplloi/basemaster"
                    )
                    null
                }
                btAdHelper -> {
                    Intent(this, AdHelperActivity::class.java)
                    intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
                }
                btFbFanpage -> {
                    LSocialUtil.likeFacebookFanpage(this)
                    null
                }
                btFrmMore -> Intent(this, MoreActivity::class.java)
                btTutorial -> Intent(this, MenuTutorialActivity::class.java)
                btPicker -> Intent(this, MenuPickerActivity::class.java)
                btNetwork -> Intent(this, NetworkActivity::class.java)
                btSecurity -> Intent(this, SecurityMenuActivity::class.java)
                btService -> Intent(this, MenuServiceActivity::class.java)
                btUtils -> Intent(this, UtilsActivity::class.java)
                else -> null
            }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
