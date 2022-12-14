package vn.loitp.app.activity.customviews.switchToggle

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_switch.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.switchToggle.appCompatWwitch.AppcompatSwitchActivity
import vn.loitp.app.activity.customviews.switchToggle.toggle.ToggleActivity
import vn.loitp.app.activity.customviews.switchToggle.toggleButtonGroup.MenuTBGActivity

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
