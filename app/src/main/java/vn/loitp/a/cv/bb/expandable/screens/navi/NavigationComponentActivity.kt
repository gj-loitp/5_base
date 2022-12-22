package vn.loitp.a.cv.bb.expandable.screens.navi

import android.os.Bundle
import androidx.navigation.Navigation
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import kotlinx.android.synthetic.main.activiy_navigation.*
import vn.loitp.R

@LogTag("NavigationComponentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class NavigationComponentActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activiy_navigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navigationController = Navigation.findNavController(this, R.id.navigationHost)

        /**
         * Call looks like NavigationUI.setupWithNavController(bottomNavigation, navigationController)
         * for native BottomNavigationView
         */
        ExpandableBottomBarNavigationUI.setupWithNavController(
            expandableBottomBar = bottomNavigation,
            navigationController = navigationController
        )
    }
}
