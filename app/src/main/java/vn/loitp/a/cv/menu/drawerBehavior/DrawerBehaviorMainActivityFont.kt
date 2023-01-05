package vn.loitp.a.cv.menu.drawerBehavior

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_drawer_behavior_main.*
import vn.loitp.R
import vn.loitp.a.cv.menu.drawerBehavior.drawer.*

@LogTag("DrawerBehaviorMainActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class DrawerBehaviorMainActivityFont : BaseActivityFont() {

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
            this.tvTitle?.text = DrawerBehaviorMainActivityFont::class.java.simpleName
        }
        buttonDefault.setSafeOnClickListener {
            launchActivity(DefaultDrawerActivityFont::class.java)
        }
        buttonAdvance1.setSafeOnClickListener {
            launchActivity(AdvanceDrawer1ActivityFont::class.java)
        }
        buttonAdvance2.setSafeOnClickListener {
            launchActivity(AdvanceDrawer2ActivityFont::class.java)
        }
        buttonAdvance3.setSafeOnClickListener {
            launchActivity(AdvanceDrawer3ActivityFont::class.java)
        }
        buttonAdvance4.setSafeOnClickListener {
            launchActivity(AdvanceDrawer4ActivityFont::class.java)
        }
        buttonAdvance5.setSafeOnClickListener {
            launchActivity(AdvanceDrawer5ActivityFont::class.java)
        }
        buttonAdvance6.setSafeOnClickListener {
            launchActivity(AdvanceDrawer6ActivityFont::class.java)
        }
        buttonAdvance3d_1.setSafeOnClickListener {
            launchActivity(Advance3DDrawer1ActivityFont::class.java)
        }
    }

}
