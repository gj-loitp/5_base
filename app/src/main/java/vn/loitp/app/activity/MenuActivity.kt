package vn.loitp.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.adhelper.AdHelperActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu.*
import loitp.basemaster.R
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
import vn.loitp.app.activity.tutorial.MenuTutorialActivity

class MenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tvPolicy = findViewById<TextView>(R.id.tv_policy)
        LUIUtil.setTextShadow(tvPolicy)
        tvPolicy.setOnClickListener { LSocialUtil.openUrlInBrowser(activity, Constants.URL_POLICY) }

        findViewById<View>(R.id.bt_api).setOnClickListener(this)
        findViewById<View>(R.id.bt_animation).setOnClickListener(this)
        findViewById<View>(R.id.bt_custom_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_demo).setOnClickListener(this)
        findViewById<View>(R.id.bt_function).setOnClickListener(this)
        findViewById<View>(R.id.bt_ads).setOnClickListener(this)
        findViewById<View>(R.id.bt_rate_app).setOnClickListener(this)
        findViewById<View>(R.id.bt_more_app).setOnClickListener(this)
        findViewById<View>(R.id.bt_database).setOnClickListener(this)
        findViewById<View>(R.id.bt_pattern).setOnClickListener(this)
        findViewById<View>(R.id.bt_chat).setOnClickListener(this)
        findViewById<View>(R.id.bt_github).setOnClickListener(this)
        findViewById<View>(R.id.bt_ad_helper).setOnClickListener(this)
        findViewById<View>(R.id.bt_fb_fanpage).setOnClickListener(this)
        findViewById<View>(R.id.bt_frm_more).setOnClickListener(this)
        findViewById<View>(R.id.bt_donation).setOnClickListener(this)
        findViewById<View>(R.id.bt_tutorial).setOnClickListener(this)
        findViewById<View>(R.id.bt_picker).setOnClickListener(this)
        btNetwork.setOnClickListener(this)
        btSecurity.setOnClickListener(this)
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

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_api -> intent = Intent(activity, MenuAPIActivity::class.java)
            R.id.bt_animation -> intent = Intent(activity, MenuAnimationActivity::class.java)
            R.id.bt_custom_view -> intent = Intent(activity, MenuCustomViewsActivity::class.java)
            R.id.bt_demo -> intent = Intent(activity, MenuDemoActivity::class.java)
            R.id.bt_ads -> intent = Intent(activity, MenuAdsActivity::class.java)
            R.id.bt_rate_app -> LSocialUtil.rateApp(activity, packageName)
            R.id.bt_more_app -> LSocialUtil.moreApp(activity)
            R.id.bt_function -> intent = Intent(activity, MenuFunctionActivity::class.java)
            R.id.bt_database -> intent = Intent(activity, MenuDatabaseActivity::class.java)
            R.id.bt_pattern -> intent = Intent(activity, MenuPatternActivity::class.java)
            R.id.bt_chat -> LSocialUtil.chatMessenger(activity)
            R.id.bt_github -> LSocialUtil.openUrlInBrowser(activity, "https://github.com/tplloi/basemaster")
            R.id.bt_ad_helper -> {
                intent = Intent(activity, AdHelperActivity::class.java)
                intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true)
            }
            R.id.bt_fb_fanpage -> LSocialUtil.likeFacebookFanpage(activity)
            R.id.bt_frm_more -> intent = Intent(activity, MoreActivity::class.java)
            R.id.bt_donation -> intent = Intent(activity, DonationActivity::class.java)
            R.id.bt_tutorial -> intent = Intent(activity, MenuTutorialActivity::class.java)
            R.id.bt_picker -> intent = Intent(activity, MenuPickerActivity::class.java)
            R.id.btNetwork -> intent = Intent(activity, NetworkActivity::class.java)
            R.id.btSecurity -> intent = Intent(activity, SecurityMenuActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
