package vn.loitp.up.a.cv.menu.drawerBehavior

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ADrawerBehaviorMainBinding
import vn.loitp.up.a.cv.menu.drawerBehavior.drawer.*

@LogTag("DrawerBehaviorMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class DrawerBehaviorMainActivity : BaseActivityFont() {

    private lateinit var binding: ADrawerBehaviorMainBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADrawerBehaviorMainBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DrawerBehaviorMainActivity::class.java.simpleName
        }
        binding.buttonDefault.setSafeOnClickListener {
            launchActivity(DefaultDrawerActivity::class.java)
        }
        binding.buttonAdvance1.setSafeOnClickListener {
            launchActivity(AdvanceDrawer1Activity::class.java)
        }
        binding.buttonAdvance2.setSafeOnClickListener {
            launchActivity(AdvanceDrawer2Activity::class.java)
        }
        binding.buttonAdvance3.setSafeOnClickListener {
            launchActivity(AdvanceDrawer3Activity::class.java)
        }
        binding.buttonAdvance4.setSafeOnClickListener {
            launchActivity(AdvanceDrawer4Activity::class.java)
        }
        binding.buttonAdvance5.setSafeOnClickListener {
            launchActivity(AdvanceDrawer5Activity::class.java)
        }
        binding.buttonAdvance6.setSafeOnClickListener {
            launchActivity(AdvanceDrawer6Activity::class.java)
        }
        binding.buttonAdvance3d1.setSafeOnClickListener {
            launchActivity(Advance3DDrawer1Activity::class.java)
        }
    }

}
