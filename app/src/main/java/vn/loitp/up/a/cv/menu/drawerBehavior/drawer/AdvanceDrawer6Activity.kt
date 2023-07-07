package vn.loitp.up.a.cv.menu.drawerBehavior.drawer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.R
import vn.loitp.databinding.ADrawerBehaviorAdvance6Binding

@LogTag("AdvanceDrawer6Activity")
@IsFullScreen(false)
class AdvanceDrawer6Activity : BaseActivityFont(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ADrawerBehaviorAdvance6Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADrawerBehaviorAdvance6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.layoutDrawer.toolbar)
        binding.layoutDrawer.fab.setSafeOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        binding.drawerLayout.let {
//            ViewCompat.setLayoutDirection(it, View.LAYOUT_DIRECTION_RTL)
            ViewCompat.setLayoutDirection(it, ViewCompat.LAYOUT_DIRECTION_RTL)
        }

        val toggle = ActionBarDrawerToggle(
            /* activity = */ this,
            /* drawerLayout = */     binding.drawerLayout,
            /* toolbar = */     binding.layoutDrawer.toolbar,
            /* openDrawerContentDescRes = */ R.string.navigation_drawer_open,
            /* closeDrawerContentDescRes = */ R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
        binding.drawerLayout.apply {
            setViewScale(GravityCompat.START, 0.9f)
            setRadius(GravityCompat.START, 35f)
            setViewElevation(GravityCompat.START, 20f)
        }
    }

//    private fun setLocale(locale: Locale) {
//        val resources = resources
//        val configuration = resources.configuration
//        val displayMetrics = resources.displayMetrics
//        configuration.setLocale(locale)
//        applicationContext.createConfigurationContext(configuration)
//    }

//    override fun onBackPressed() {
//        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
//            drawerLayout?.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun onBaseBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBaseBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_drawer_behavior, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionRightDrawer -> {
                binding.drawerLayout.openDrawer(GravityCompat.END)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
