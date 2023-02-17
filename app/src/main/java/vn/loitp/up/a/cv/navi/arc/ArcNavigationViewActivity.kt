package vn.loitp.up.a.cv.navi.arc

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.ANaviArcNavigationViewBinding

@LogTag("ArcNavigationViewActivity")
@IsFullScreen(false)
class ArcNavigationViewActivity :
    BaseActivityFont(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ANaviArcNavigationViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANaviArcNavigationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.layoutNavigation.toolbar)
        binding.layoutNavigation.fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Replace with your own action",
                Snackbar.LENGTH_LONG
            ).setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            /* activity = */ this,
            /* drawerLayout = */ binding.drawerLayout,
            /* toolbar = */ binding.layoutNavigation.toolbar,
            /* openDrawerContentDescRes = */ R.string.navigation_drawer_open,
            /* closeDrawerContentDescRes = */ R.string.navigation_drawer_close
        )
        toggle.syncState()

        binding.navViewRight.setNavigationItemSelectedListener(this)
        binding.navView.setNavigationItemSelectedListener(this)
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
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            binding.drawerLayout.isDrawerOpen(GravityCompat.END) -> {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
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
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END)
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
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
