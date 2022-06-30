package vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_drawer_behavior_advance6.*
import kotlinx.android.synthetic.main.view_drawer_behavior_app_bar_default.*
import vn.loitp.app.R
import java.util.*

@LogTag("AdvanceDrawer6Activity")
@IsFullScreen(false)
class AdvanceDrawer6Activity : BaseFontActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_drawer_behavior_advance6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        drawerLayout?.let {
            ViewCompat.setLayoutDirection(it, View.LAYOUT_DIRECTION_RTL)
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        drawerLayout?.apply {
            setViewScale(GravityCompat.START, 0.9f)
            setRadius(GravityCompat.START, 35f)
            setViewElevation(GravityCompat.START, 20f)
        }
    }

    private fun setLocale(locale: Locale) {
        val resources = resources
        val configuration = resources.configuration
        val displayMetrics = resources.displayMetrics
        configuration.setLocale(locale)
        applicationContext.createConfigurationContext(configuration)
    }

    override fun onBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawerLayout?.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_drawer_behavior, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionRightDrawer -> {
                drawerLayout?.openDrawer(GravityCompat.END)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
