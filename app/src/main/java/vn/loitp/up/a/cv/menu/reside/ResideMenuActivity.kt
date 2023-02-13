package vn.loitp.up.a.cv.menu.reside

import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.views.menu.resideMenu.ResideMenu
import com.loitp.views.menu.resideMenu.ResideMenuItem
import vn.loitp.R
import vn.loitp.databinding.AResideMenuBinding

@LogTag("ResideMenuActivity")
@IsFullScreen(false)
class ResideMenuActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: AResideMenuBinding

    var resideMenu: ResideMenu? = null
    private var itemHome: ResideMenuItem? = null
    private var itemProfile: ResideMenuItem? = null
    private var itemCalendar: ResideMenuItem? = null
    private var itemSettings: ResideMenuItem? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AResideMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpMenu()
        if (savedInstanceState == null) {
            changeFragment(HomeFragment())
        }
    }

    private fun setupUIResideMenuItem(resideMenuItem: ResideMenuItem) {
        resideMenuItem.setTextColor(Color.BLACK)
        resideMenuItem.setTextShadow(Color.WHITE)
        resideMenuItem.setTextSize(resources.getDimension(R.dimen.txt_medium))
        resideMenuItem.setIvIconSizeDp(20)
    }

    private fun setUpMenu() {
        resideMenu = ResideMenu(this)
        resideMenu?.apply {
            this.realtimeBlurView.setBlurRadius(50f)
            this.realtimeBlurView.setOverlayColor(getColor(R.color.black65))
            this.setUse3D(true)
            this.setBackground(R.drawable.iv)
            this.attachToActivity(this@ResideMenuActivity)
            this.menuListener = menuListener
            // valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
            this.setScaleValue(0.6f)
        }

        // create menu items;
        itemHome = ResideMenuItem(this, R.drawable.ic_launcher_loitp, "Home")
        itemProfile = ResideMenuItem(this, R.drawable.ic_launcher_loitp, "Profile")
        itemCalendar = ResideMenuItem(this, R.drawable.ic_launcher_loitp, "Calendar")
        itemSettings = ResideMenuItem(this, R.drawable.ic_launcher_loitp, "Settings")

        itemHome?.let {
            setupUIResideMenuItem(it)
        }
        itemProfile?.let {
            setupUIResideMenuItem(it)
        }
        itemCalendar?.let {
            setupUIResideMenuItem(it)
        }
        itemSettings?.let {
            setupUIResideMenuItem(it)
        }

        itemHome?.setOnClickListener(this)
        itemProfile?.setOnClickListener(this)
        itemCalendar?.setOnClickListener(this)
        itemSettings?.setOnClickListener(this)

        resideMenu?.apply {
            this.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT)
            this.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT)
            this.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT)
            this.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT)
        }

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        binding.titleBarLeftMenu.setOnClickListener {
            resideMenu?.openMenu(ResideMenu.DIRECTION_LEFT)
        }
        binding.titleBarRightMenu.setOnClickListener {
            resideMenu?.openMenu(ResideMenu.DIRECTION_RIGHT)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return resideMenu?.dispatchTouchEvent(ev) ?: false
    }

    override fun onClick(view: View) {
        when {
            view === itemHome -> {
                changeFragment(HomeFragment())
            }
            view === itemProfile -> {
                changeFragment(ProfileFragment())
            }
            view === itemCalendar -> {
                changeFragment(CalendarFragment())
            }
            view === itemSettings -> {
                changeFragment(SettingsFragment())
            }
        }
        resideMenu?.closeMenu()
    }

//    private val menuListener: OnMenuListener = object : OnMenuListener {
//        override fun openMenu() {
//            showShortInformation("Menu is opened!", true)
//        }
//
//        override fun closeMenu() {
//            showShortInformation("Menu is closed!", true)
//        }
//    }

    private fun changeFragment(targetFragment: Fragment) {
        resideMenu?.clearIgnoredViewList()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainFragment, targetFragment, "fragment")
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
