package vn.loitp.a.cv.sw

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_sw_menu.*
import vn.loitp.R
import vn.loitp.a.cv.sw.appCompat.AppcompatSwitchActivityFont
import vn.loitp.a.cv.sw.toggle.ToggleActivityFont
import vn.loitp.a.cv.sw.toggleButtonGroup.MenuTBGActivityFont

@LogTag("SwitchToggleMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSwitchToggleActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_sw_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSwitchToggleActivityFont::class.java.simpleName
        }

        btAppcompatSwitch.setSafeOnClickListener {
            launchActivity(AppcompatSwitchActivityFont::class.java)
        }
        btToggle.setSafeOnClickListener {
            launchActivity(ToggleActivityFont::class.java)
        }
        btToggleButtonGroup.setSafeOnClickListener {
            launchActivity(MenuTBGActivityFont::class.java)
        }
    }
}
