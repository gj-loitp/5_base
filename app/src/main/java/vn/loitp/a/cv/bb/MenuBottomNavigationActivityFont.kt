package vn.loitp.a.cv.bb

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_bottom_navigation_bar.*
import vn.loitp.R
import vn.loitp.a.cv.bb.bottomBar.BottomBarActivityFont
import vn.loitp.a.cv.bb.expandable.ShowCaseActivityFont

@LogTag("BottomNavigationMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuBottomNavigationActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_bottom_navigation_bar
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
            this.tvTitle?.text = MenuBottomNavigationActivityFont::class.java.simpleName
        }
        btBottomBarBlur.setSafeOnClickListener {
            launchActivity(BottomBarActivityFont::class.java)
        }
        btExpandableBottomBar.setSafeOnClickListener {
            launchActivity(ShowCaseActivityFont::class.java)
        }
    }
}
