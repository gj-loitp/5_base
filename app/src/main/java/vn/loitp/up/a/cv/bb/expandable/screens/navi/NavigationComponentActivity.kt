package vn.loitp.up.a.cv.bb.expandable.screens.navi

import android.os.Bundle
import androidx.navigation.Navigation
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import vn.loitp.R
import vn.loitp.databinding.ANavigationBinding

@LogTag("NavigationComponentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class NavigationComponentActivity : BaseActivityFont() {
    private lateinit var binding: ANavigationBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationController = Navigation.findNavController(this, R.id.navigationHost)

        /**
         * Call looks like NavigationUI.setupWithNavController(bottomNavigation, navigationController)
         * for native BottomNavigationView
         */
        ExpandableBottomBarNavigationUI.setupWithNavController(
            expandableBottomBar = binding.bottomNavigation,
            navigationController = navigationController
        )
    }
}
