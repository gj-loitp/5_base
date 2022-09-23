package vn.loitp.app.activity.customviews.navigation.arcNavigationView

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_arc_navigation_view.*
import kotlinx.android.synthetic.main.menu_arc_navigation_view_main.*
import vn.loitp.app.R

@LogTag("ArcNavigationViewActivity")
@IsFullScreen(false)
class ArcNavigationViewActivity :
    BaseFontActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_arc_navigation_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Replace with your own action",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.syncState()

        navViewRight.setNavigationItemSelectedListener(this)
        navView.setNavigationItemSelectedListener(this)
    }

//    override fun onBackPressed() {
//        when {
//            drawerLayout.isDrawerOpen(GravityCompat.START) -> {
//                drawerLayout.closeDrawer(GravityCompat.START)
//            }
//            drawerLayout.isDrawerOpen(GravityCompat.END) -> {
//                drawerLayout.closeDrawer(GravityCompat.END)
//            }
//            else -> {
//                super.onBackPressed()
//            }
//        }
//    }

    override fun onBaseBackPressed() {
        when {
            drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            drawerLayout.isDrawerOpen(GravityCompat.END) -> {
                drawerLayout.closeDrawer(GravityCompat.END)
            }
            else -> {
                super.onBaseBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.itemActionRightMenu) {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navCamera -> {
                // Handle the camera action
            }
            R.id.navGallery -> {
            }
            R.id.navSlideshow -> {
            }
            R.id.navManage -> {
            }
            R.id.navShare -> {
            }
            R.id.navSend -> {
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
