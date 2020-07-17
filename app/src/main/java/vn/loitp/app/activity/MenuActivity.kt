package vn.loitp.app.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
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

class MenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LUIUtil.setTextShadow(tvPolicy)
        tvPolicy.setOnClickListener { LSocialUtil.openUrlInBrowser(context = activity, url = Constants.URL_POLICY) }

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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        showShort("Press again to exit")
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btApi -> intent = Intent(activity, MenuAPIActivity::class.java)
            btAnimation -> intent = Intent(activity, MenuAnimationActivity::class.java)
            btCustomView -> intent = Intent(activity, MenuCustomViewsActivity::class.java)
            btDemo -> intent = Intent(activity, MenuDemoActivity::class.java)
            btAds -> intent = Intent(activity, MenuAdsActivity::class.java)
            btRateApp -> LSocialUtil.rateApp(activity, packageName)
            btMoreApp -> LSocialUtil.moreApp(activity)
            btFunction -> intent = Intent(activity, MenuFunctionActivity::class.java)
            btDatabase -> intent = Intent(activity, MenuDatabaseActivity::class.java)
            btPattern -> intent = Intent(activity, MenuPatternActivity::class.java)
            btChat -> LSocialUtil.chatMessenger(activity)
            btGithub -> LSocialUtil.openUrlInBrowser(activity, "https://github.com/tplloi/basemaster")
            btAdHelper -> {
                intent = Intent(activity, AdHelperActivity::class.java)
                intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
            }
            btFbFanpage -> LSocialUtil.likeFacebookFanpage(activity)
            btFrmMore -> intent = Intent(activity, MoreActivity::class.java)
            btDonation -> intent = Intent(activity, DonationActivity::class.java)
            btTutorial -> intent = Intent(activity, MenuTutorialActivity::class.java)
            btPicker -> intent = Intent(activity, MenuPickerActivity::class.java)
            btNetwork -> intent = Intent(activity, NetworkActivity::class.java)
            btSecurity -> intent = Intent(activity, SecurityMenuActivity::class.java)
            btService -> intent = Intent(activity, MenuServiceActivity::class.java)
            btUtils -> intent = Intent(activity, UtilsActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
