package vn.loitp.app.activity.customviews.actionbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
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

@LogTag("ActionbarMenuActivity")
@IsFullScreen(false)
class ActionbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_action_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews(){
        bt_collapsingtoolbarlayout.setOnClickListener(this)
        bt_l_actionbar.setOnClickListener(this)
        bt_collapsingtoolbarwithtablayout.setOnClickListener(this)
        bt_navigation_view.setOnClickListener(this)
        bt_navigation_view_with_text.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            bt_collapsingtoolbarlayout ->
                intent = Intent(this, CollapsingToolbarLayoutActivity::class.java)
            bt_collapsingtoolbarwithtablayout ->
                intent = Intent(this, CollapsingToolbarWithTabLayoutActivity::class.java)
            bt_l_actionbar ->
                intent = Intent(this, LActionbarActivity::class.java)
            bt_navigation_view ->
                intent = Intent(this, NavigationViewActivity::class.java)
            bt_navigation_view_with_text ->
                intent = Intent(this, NavigationViewWithTextActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
