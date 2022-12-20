package vn.loitp.app.activity.customviews.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.menu.drawerBehavior.DrawerBehaviorMainActivity
import vn.loitp.app.activity.customviews.menu.resideMenu.ResideMenuActivity

@LogTag("MenuMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_menu
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
        btResideMenu.setOnClickListener(this)
        btDrawerBehavior.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btResideMenu -> intent = Intent(this, ResideMenuActivity::class.java)
            btDrawerBehavior -> intent = Intent(this, DrawerBehaviorMainActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
