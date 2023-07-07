package vn.loitp.up.a.cv.menu.drawerBehavior.drawer

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
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.R
import vn.loitp.databinding.ADrawerBehaviorAdvance4Binding

@LogTag("AdvanceDrawer4Activity")
@IsFullScreen(false)
class AdvanceDrawer4Activity : BaseActivityFont(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ADrawerBehaviorAdvance4Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADrawerBehaviorAdvance4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        setSupportActionBar(binding.layoutDrawer.toolbar)
        binding.layoutDrawer.fab.setSafeOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val toggle = ActionBarDrawerToggle(
            /* activity = */ this,
            /* drawerLayout = */ binding.drawerLayout,
            /* toolbar = */ binding.layoutDrawer.toolbar,
            /* openDrawerContentDescRes = */ R.string.navigation_drawer_open,
            /* closeDrawerContentDescRes = */ R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
        binding.drawerLayout.apply {
            setViewScale(Gravity.END, 0.9f)
            setViewElevation(Gravity.END, 20f)
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
