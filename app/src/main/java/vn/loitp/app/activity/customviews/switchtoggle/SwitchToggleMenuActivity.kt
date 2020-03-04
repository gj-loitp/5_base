package vn.loitp.app.activity.customviews.switchtoggle

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_switch_toggle.*

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch.AppcompatSwitchActivity
import vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton.CustomToggleButtonActivity
import vn.loitp.app.activity.customviews.switchtoggle.toggle.ToggleActivity
import vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup.TBGMenuActivity

class SwitchToggleMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btAppcompatSwitch.setOnClickListener(this)
        btCustomToggleButton.setOnClickListener(this)
        btToggle.setOnClickListener(this)
        btToggleButtonGroup.setOnClickListener(this)
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
        when (v) {
            btAppcompatSwitch -> intent = Intent(activity, AppcompatSwitchActivity::class.java)
            btCustomToggleButton -> intent = Intent(activity, CustomToggleButtonActivity::class.java)
            btToggle -> intent = Intent(activity, ToggleActivity::class.java)
            btToggleButtonGroup -> intent = Intent(activity, TBGMenuActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
