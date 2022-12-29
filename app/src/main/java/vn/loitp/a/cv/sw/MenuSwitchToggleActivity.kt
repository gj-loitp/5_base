package vn.loitp.a.cv.sw

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_sw_menu.*
import vn.loitp.R
import vn.loitp.a.cv.sw.appCompat.AppcompatSwitchActivity
import vn.loitp.a.cv.sw.toggle.ToggleActivity
import vn.loitp.a.cv.sw.toggleButtonGroup.MenuTBGActivity

@LogTag("SwitchToggleMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSwitchToggleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sw_menu
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

        btAppcompatSwitch.setSafeOnClickListener {
            launchActivity(AppcompatSwitchActivity::class.java)
        }
        btToggle.setSafeOnClickListener {
            launchActivity(ToggleActivity::class.java)
        }
        btToggleButtonGroup.setSafeOnClickListener {
            launchActivity(MenuTBGActivity::class.java)
        }
    }
}
