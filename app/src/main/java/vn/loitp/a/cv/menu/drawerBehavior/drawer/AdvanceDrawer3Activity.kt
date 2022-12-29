package vn.loitp.a.cv.menu.drawerBehavior.drawer

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.a_drawer_behavior_advance3.*
import kotlinx.android.synthetic.main.view_drawer_behavior_app_bar_default.*
import vn.loitp.R

@LogTag("AdvanceDrawer3Activity")
@IsFullScreen(false)
class AdvanceDrawer3Activity : BaseFontActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_drawer_behavior_advance3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        fab.setSafeOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
            /* activity = */ this,
            /* drawerLayout = */ drawerLayout,
            /* toolbar = */ toolbar,
            /* openDrawerContentDescRes = */ R.string.navigation_drawer_open,
            /* closeDrawerContentDescRes = */ R.string.navigation_drawer_close
        )
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
        drawerLayout?.apply {
            setViewScale(Gravity.START, 0.9f)
            setViewElevation(Gravity.START, 20f)
            useCustomBehavior(Gravity.END)
        }
    }

//    override fun onBackPressed() {
//        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
//            drawerLayout?.closeDrawer(GravityCompat.START)
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun onBaseBackPressed() {
        if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            super.onBaseBackPressed()
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
