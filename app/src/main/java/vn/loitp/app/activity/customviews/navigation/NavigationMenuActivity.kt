package vn.loitp.app.activity.customviews.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.navigation.arcnavigationview.ArcNavigationViewActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class NavigationMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_arc_navigation).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_navigation_view
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_arc_navigation -> intent = Intent(activity, ArcNavigationViewActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
