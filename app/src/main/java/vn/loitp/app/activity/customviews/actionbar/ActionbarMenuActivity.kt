package vn.loitp.app.activity.customviews.actionbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayout.CollapsingToolbarLayoutActivity
import vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayoutwithtablayout.CollapsingToolbarWithTabLayoutActivity
import vn.loitp.app.activity.customviews.actionbar.lactionbar.LActionbarActivity
import vn.loitp.app.activity.customviews.actionbar.navigationview.NavigationViewActivity
import vn.loitp.app.activity.customviews.actionbar.navigationviewwithtext.NavigationViewWithTextActivity

class ActionbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_collapsingtoolbarlayout).setOnClickListener(this)
        findViewById<View>(R.id.bt_l_actionbar).setOnClickListener(this)
        findViewById<View>(R.id.bt_collapsingtoolbarwithtablayout).setOnClickListener(this)
        findViewById<View>(R.id.bt_navigation_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_navigation_view_with_text).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_action_bar
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_collapsingtoolbarlayout -> intent = Intent(activity, CollapsingToolbarLayoutActivity::class.java)
            R.id.bt_collapsingtoolbarwithtablayout -> intent = Intent(activity, CollapsingToolbarWithTabLayoutActivity::class.java)
            R.id.bt_l_actionbar -> intent = Intent(activity, LActionbarActivity::class.java)
            R.id.bt_navigation_view -> intent = Intent(activity, NavigationViewActivity::class.java)
            R.id.bt_navigation_view_with_text -> intent = Intent(activity, NavigationViewWithTextActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
