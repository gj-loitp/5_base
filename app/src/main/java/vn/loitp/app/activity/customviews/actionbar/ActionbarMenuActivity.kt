package vn.loitp.app.activity.customviews.actionbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
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

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ActionbarMenuActivity::class.java.simpleName
        }
        btCollapsingToolBarLayout.setOnClickListener(this)
        btLActionBar.setOnClickListener(this)
        btCollapsingToolbarWithTabLayout.setOnClickListener(this)
        btNavigationView.setOnClickListener(this)
        btNavigationViewWithText.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btCollapsingToolBarLayout ->
                Intent(this, CollapsingToolbarLayoutActivity::class.java)
            btCollapsingToolbarWithTabLayout ->
                Intent(this, CollapsingToolbarWithTabLayoutActivity::class.java)
            btLActionBar ->
                Intent(this, LActionbarActivity::class.java)
            btNavigationView ->
                Intent(this, NavigationViewActivity::class.java)
            btNavigationViewWithText ->
                Intent(this, NavigationViewWithTextActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
