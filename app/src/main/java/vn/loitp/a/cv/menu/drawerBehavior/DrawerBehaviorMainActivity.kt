package vn.loitp.a.cv.menu.drawerBehavior

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_drawer_behavior_main.*
import vn.loitp.R
import vn.loitp.a.cv.menu.drawerBehavior.drawer.*

@LogTag("DrawerBehaviorMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class DrawerBehaviorMainActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_drawer_behavior_main
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DrawerBehaviorMainActivity::class.java.simpleName
        }
        buttonDefault.setSafeOnClickListener {
            launchActivity(DefaultDrawerActivity::class.java)
        }
        buttonAdvance1.setSafeOnClickListener {
            launchActivity(AdvanceDrawer1Activity::class.java)
        }
        buttonAdvance2.setSafeOnClickListener {
            launchActivity(AdvanceDrawer2Activity::class.java)
        }
        buttonAdvance3.setSafeOnClickListener {
            launchActivity(AdvanceDrawer3Activity::class.java)
        }
        buttonAdvance4.setSafeOnClickListener {
            launchActivity(AdvanceDrawer4Activity::class.java)
        }
        buttonAdvance5.setSafeOnClickListener {
            launchActivity(AdvanceDrawer5Activity::class.java)
        }
        buttonAdvance6.setSafeOnClickListener {
            launchActivity(AdvanceDrawer6Activity::class.java)
        }
        buttonAdvance3d_1.setSafeOnClickListener {
            launchActivity(Advance3DDrawer1Activity::class.java)
        }
    }

}
