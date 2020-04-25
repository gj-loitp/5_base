package vn.loitp.app.activity.customviews.menu

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.menu.drawerbehavior.DrawerBehaviorMainActivity
import vn.loitp.app.activity.customviews.menu.residemenu.ResideMenuActivity

class MenuMenuActivity : BaseFontActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btResideMenu.setOnClickListener(this)
        btDrawerBehavior.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btResideMenu -> intent = Intent(activity, ResideMenuActivity::class.java)
            btDrawerBehavior -> intent = Intent(activity, DrawerBehaviorMainActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
