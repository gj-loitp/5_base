package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import github.com.st235.lib_expandablebottombar.ExpandableBottomBar
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import vn.loitp.app.R

class NavigationComponentActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: ExpandableBottomBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activiy_navigation)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        val navigationController = Navigation.findNavController(this, R.id.navigationHost)

        /**
         * Call looks like NavigationUI.setupWithNavController(bottomNavigation, navigationController)
         * for native BottomNavigationView
         */
        ExpandableBottomBarNavigationUI.setupWithNavController(
            bottomNavigation,
            navigationController
        )
    }
}
