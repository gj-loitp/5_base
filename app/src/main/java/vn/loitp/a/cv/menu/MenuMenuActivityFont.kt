package vn.loitp.a.cv.menu

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_menu_menu.*
import vn.loitp.R
import vn.loitp.a.cv.menu.drawerBehavior.DrawerBehaviorMainActivityFont
import vn.loitp.a.cv.menu.reside.ResideMenuActivityFont

@LogTag("MenuMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMenuActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_menu
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuMenuActivityFont::class.java.simpleName
        }
        btResideMenu.setSafeOnClickListener {
            launchActivity(ResideMenuActivityFont::class.java)
        }
        btDrawerBehavior.setSafeOnClickListener {
            launchActivity(DrawerBehaviorMainActivityFont::class.java)
        }
    }
}
