package vn.loitp.app.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.annotation.IsAutoAnimation
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.ads.MenuAdsActivity
import vn.loitp.app.activity.animation.MenuAnimationActivity
import vn.loitp.app.activity.api.MenuAPIActivity
import vn.loitp.app.activity.customviews.MenuCustomViewsActivity
import vn.loitp.app.activity.database.MenuDatabaseActivity
import vn.loitp.app.activity.demo.MenuDemoActivity
import vn.loitp.app.activity.donation.DonationActivity
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
        btDonation.setOnClickListener(this)
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
        showShortInformation(msg = getString(R.string.press_again_to_exit), isTopAnchor = false)
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btApi -> intent = Intent(this, MenuAPIActivity::class.java)
            btAnimation -> intent = Intent(this, MenuAnimationActivity::class.java)
            btCustomView -> intent = Intent(this, MenuCustomViewsActivity::class.java)
            btDemo -> intent = Intent(this, MenuDemoActivity::class.java)
            btAds -> intent = Intent(this, MenuAdsActivity::class.java)
            btRateApp -> LSocialUtil.rateApp(this, packageName)
            btMoreApp -> LSocialUtil.moreApp(this)
            btFunction -> intent = Intent(this, MenuFunctionActivity::class.java)
            btDatabase -> intent = Intent(this, MenuDatabaseActivity::class.java)
            btPattern -> intent = Intent(this, MenuPatternActivity::class.java)
            btChat -> LSocialUtil.chatMessenger(this)
            btGithub -> LSocialUtil.openUrlInBrowser(this, "https://github.com/tplloi/basemaster")
            btAdHelper -> {
                intent = Intent(this, AdHelperActivity::class.java)
                intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
            }
            btFbFanpage -> LSocialUtil.likeFacebookFanpage(this)
            btFrmMore -> intent = Intent(this, MoreActivity::class.java)
            btDonation -> intent = Intent(this, DonationActivity::class.java)
            btTutorial -> intent = Intent(this, MenuTutorialActivity::class.java)
            btPicker -> intent = Intent(this, MenuPickerActivity::class.java)
            btNetwork -> intent = Intent(this, NetworkActivity::class.java)
            btSecurity -> intent = Intent(this, SecurityMenuActivity::class.java)
            btService -> intent = Intent(this, MenuServiceActivity::class.java)
            btUtils -> intent = Intent(this, UtilsActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
