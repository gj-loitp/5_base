package vn.loitp.app.a.cv.switchToggle

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_switch.*
import vn.loitp.R
import vn.loitp.app.a.cv.switchToggle.appCompatWwitch.AppcompatSwitchActivity
import vn.loitp.app.a.cv.switchToggle.toggle.ToggleActivity
import vn.loitp.app.a.cv.switchToggle.toggleButtonGroup.MenuTBGActivity

@LogTag("SwitchToggleMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSwitchToggleActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_switch
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSwitchToggleActivity::class.java.simpleName
        }

        btAppcompatSwitch.setOnClickListener(this)
        btToggle.setOnClickListener(this)
        btToggleButtonGroup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAppcompatSwitch -> intent = Intent(this, AppcompatSwitchActivity::class.java)
            btToggle -> intent = Intent(this, ToggleActivity::class.java)
            btToggleButtonGroup -> intent = Intent(this, MenuTBGActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
