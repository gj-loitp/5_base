package vn.loitp.app.activity.customviews.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.menu.drawerbehavior.DrawerBehaviorMainActivity
import vn.loitp.app.activity.customviews.menu.residemenu.ResideMenuActivity

@LayoutId(R.layout.activity_menu_menu)
@LogTag("MenuMenuActivity")
@IsFullScreen(false)
class MenuMenuActivity : BaseFontActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
