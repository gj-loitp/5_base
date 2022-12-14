package vn.loitp.app.activity.demo.fragmentNavigation

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_fragment_navigation.*
import vn.loitp.app.R

@LogTag("FragmentNavigationActivity")
@IsFullScreen(false)
class FragmentNavigationActivity :
    BaseFontActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    val T = "FragmentNavigationActivity"

    val navController: NavController
        get() = Navigation.findNavController(this, R.id.fragmentContainerView)

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fragment_navigation
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
            this.tvTitle?.text = FragmentNavigationActivity::class.java.simpleName
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        logD("onNavigationItemSelected " + menuItem.title)
        return true
    }

    override fun onBaseBackPressed() {
//        super.onBaseBackPressed()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        if (navHostFragment == null) {
            super.onBaseBackPressed()//correct
            return
        }
        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
        if (currentFragment is OnBackPressedListener) {
            (currentFragment as OnBackPressedListener).onBackPressed()
        }
    }

//    override fun onBackPressed() {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
//        if (navHostFragment == null) {
//            super.onBackPressed()
//            return
//        }
//        val currentFragment = navHostFragment.childFragmentManager.fragments[0]
//        if (currentFragment is OnBackPressedListener) {
//            (currentFragment as OnBackPressedListener).onBackPressed()
//        }
//    }

    fun popThisFragment() {
        if (!navController.popBackStack()) {
//            super.onBackPressed()
            super.onBaseBackPressed()
        }
    }
}
