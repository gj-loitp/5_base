package vn.loitp.app.activity.customviews.actionbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_action_bar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.actionbar.collapsingToolbarLayout.CollapsingToolbarLayoutActivity
import vn.loitp.app.activity.customviews.actionbar.collapsingToolbarLayoutWithTabLayout.CollapsingToolbarWithTabLayoutActivity
import vn.loitp.app.activity.customviews.actionbar.lActionbar.LActionbarActivity
import vn.loitp.app.activity.customviews.actionbar.navigationView.NavigationViewActivity
import vn.loitp.app.activity.customviews.actionbar.navigationViewWithText.NavigationViewWithTextActivity

@LogTag("MenuActionBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuActionBarActivity : BaseFontActivity(), View.OnClickListener {

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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuActionBarActivity::class.java.simpleName
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
