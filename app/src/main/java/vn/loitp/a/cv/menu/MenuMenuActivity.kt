package vn.loitp.a.cv.menu

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_menu.*
import vn.loitp.R
import vn.loitp.a.cv.menu.drawerBehavior.DrawerBehaviorMainActivity
import vn.loitp.a.cv.menu.reside.ResideMenuActivity

@LogTag("MenuMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_menu
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
            this.tvTitle?.text = MenuMenuActivity::class.java.simpleName
        }
        btResideMenu.setSafeOnClickListener {
            launchActivity(ResideMenuActivity::class.java)
        }
        btDrawerBehavior.setSafeOnClickListener {
            launchActivity(DrawerBehaviorMainActivity::class.java)
        }
    }
}
