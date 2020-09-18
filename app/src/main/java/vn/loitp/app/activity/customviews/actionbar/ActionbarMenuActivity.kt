package vn.loitp.app.activity.customviews.actionbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_action_bar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayout.CollapsingToolbarLayoutActivity
import vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayoutwithtablayout.CollapsingToolbarWithTabLayoutActivity
import vn.loitp.app.activity.customviews.actionbar.lactionbar.LActionbarActivity
import vn.loitp.app.activity.customviews.actionbar.navigationview.NavigationViewActivity
import vn.loitp.app.activity.customviews.actionbar.navigationviewwithtext.NavigationViewWithTextActivity

@LayoutId(R.layout.activity_menu_action_bar)
@LogTag("ActionbarMenuActivity")
class ActionbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_collapsingtoolbarlayout.setOnClickListener(this)
        bt_l_actionbar.setOnClickListener(this)
        bt_collapsingtoolbarwithtablayout.setOnClickListener(this)
        bt_navigation_view.setOnClickListener(this)
        bt_navigation_view_with_text.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            bt_collapsingtoolbarlayout ->
                intent = Intent(activity, CollapsingToolbarLayoutActivity::class.java)
            bt_collapsingtoolbarwithtablayout ->
                intent = Intent(activity, CollapsingToolbarWithTabLayoutActivity::class.java)
            bt_l_actionbar ->
                intent = Intent(activity, LActionbarActivity::class.java)
            bt_navigation_view ->
                intent = Intent(activity, NavigationViewActivity::class.java)
            bt_navigation_view_with_text ->
                intent = Intent(activity, NavigationViewWithTextActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
