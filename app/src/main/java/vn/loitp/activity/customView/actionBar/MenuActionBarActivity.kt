package vn.loitp.activity.customView.actionBar

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_action_bar.*
import vn.loitp.R
import vn.loitp.activity.customView.actionBar.collapsingToolbarLayout.CollapsingToolbarLayoutActivity
import vn.loitp.activity.customView.actionBar.collapsingToolbarLayoutWithTabLayout.CollapsingToolbarWithTabLayoutActivity
import vn.loitp.activity.customView.actionBar.l.LActionbarActivity
import vn.loitp.activity.customView.actionBar.navigationView.NavigationViewActivity
import vn.loitp.activity.customView.actionBar.navigationViewWithText.NavigationViewWithTextActivity

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
        when (v) {
            btCollapsingToolBarLayout -> launchActivity(CollapsingToolbarLayoutActivity::class.java)
            btCollapsingToolbarWithTabLayout -> launchActivity(
                CollapsingToolbarWithTabLayoutActivity::class.java
            )
            btLActionBar -> launchActivity(LActionbarActivity::class.java)
            btNavigationView -> launchActivity(NavigationViewActivity::class.java)
            btNavigationViewWithText -> launchActivity(NavigationViewWithTextActivity::class.java)

        }

    }
}
