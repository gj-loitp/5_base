package vn.loitp.up.a.cv.sw

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.cv.sw.appCompat.AppcompatSwitchActivityFont
import vn.loitp.a.cv.sw.toggle.ToggleActivityFont
import vn.loitp.a.cv.sw.toggleButtonGroup.MenuTBGActivityFont
import vn.loitp.databinding.ASwMenuBinding

@LogTag("SwitchToggleMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuSwitchToggleActivity : BaseActivityFont() {

    private lateinit var binding: ASwMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASwMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuSwitchToggleActivity::class.java.simpleName
        }

        binding.btAppcompatSwitch.setSafeOnClickListener {
            launchActivity(AppcompatSwitchActivityFont::class.java)
        }
        binding.btToggle.setSafeOnClickListener {
            launchActivity(ToggleActivityFont::class.java)
        }
        binding.btToggleButtonGroup.setSafeOnClickListener {
            launchActivity(MenuTBGActivityFont::class.java)
        }
    }
}
