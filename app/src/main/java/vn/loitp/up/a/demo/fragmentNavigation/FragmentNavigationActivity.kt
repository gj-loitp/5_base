package vn.loitp.up.a.demo.fragmentNavigation

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.OnBackPressedListener
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFrmNavigationBinding

@LogTag("FragmentNavigationActivity")
@IsFullScreen(false)
class FragmentNavigationActivity :
    BaseActivityFont(),
    NavigationView.OnNavigationItemSelectedListener {

    val T = "FragmentNavigationActivity"

    val navController: NavController
        get() = Navigation.findNavController(this, R.id.fragmentContainerView)

    private lateinit var binding: AFrmNavigationBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFrmNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
