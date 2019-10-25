package vn.loitp.app.activity.customviews.switchtoggle

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch.AppcompatSwitchActivity
import vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton.CustomToggleButtonActivity
import vn.loitp.app.activity.customviews.switchtoggle.toggle.ToggleActivity

class SwitchToggleMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_appcompat_switch).setOnClickListener(this)
        findViewById<View>(R.id.bt_custom_toggle_button).setOnClickListener(this)
        findViewById<View>(R.id.bt_toggle).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_switch_toggle
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_appcompat_switch -> intent = Intent(activity, AppcompatSwitchActivity::class.java)
            R.id.bt_custom_toggle_button -> intent = Intent(activity, CustomToggleButtonActivity::class.java)
            R.id.bt_toggle -> intent = Intent(activity, ToggleActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
