package vn.loitp.app.activity.customviews.switchtoggle

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_switch_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.switchtoggle.appcompatswitch.AppcompatSwitchActivity
import vn.loitp.app.activity.customviews.switchtoggle.toggle.ToggleActivity
import vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup.TBGMenuActivity

@LogTag("SwitchToggleMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class SwitchToggleMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btAppcompatSwitch.setOnClickListener(this)
        btToggle.setOnClickListener(this)
        btToggleButtonGroup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAppcompatSwitch -> intent = Intent(this, AppcompatSwitchActivity::class.java)
            btToggle -> intent = Intent(this, ToggleActivity::class.java)
            btToggleButtonGroup -> intent = Intent(this, TBGMenuActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
