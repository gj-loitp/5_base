package vn.loitp.a.cv.ab

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_menu_action_bar.*
import vn.loitp.R
import vn.loitp.a.cv.ab.collapsingToolbarLayout.CollapsingToolbarLayoutActivityFont
import vn.loitp.a.cv.ab.collapsingToolbarLayoutWithTabLayout.CollapsingToolbarWithTabLayoutActivityFont
import vn.loitp.a.cv.ab.l.LActionbarActivityFont
import vn.loitp.a.cv.ab.navigationView.NavigationViewActivityFont
import vn.loitp.a.cv.ab.navigationViewWithText.NavigationViewWithTextActivityFont

@LogTag("MenuActionBarActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuActionBarActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_action_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuActionBarActivityFont::class.java.simpleName
        }
        btCollapsingToolBarLayout.setOnClickListener(this)
        btLActionBar.setOnClickListener(this)
        btCollapsingToolbarWithTabLayout.setOnClickListener(this)
        btNavigationView.setOnClickListener(this)
        btNavigationViewWithText.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btCollapsingToolBarLayout -> launchActivity(CollapsingToolbarLayoutActivityFont::class.java)
            btCollapsingToolbarWithTabLayout -> launchActivity(
                CollapsingToolbarWithTabLayoutActivityFont::class.java
            )
            btLActionBar -> launchActivity(LActionbarActivityFont::class.java)
            btNavigationView -> launchActivity(NavigationViewActivityFont::class.java)
            btNavigationViewWithText -> launchActivity(NavigationViewWithTextActivityFont::class.java)

        }

    }
}
