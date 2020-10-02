package vn.loitp.app.activity.customviews.menu.residemenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LAppResource;
import com.views.menu.residemenu.ResideMenu;
import com.views.menu.residemenu.ResideMenuItem;

import vn.loitp.app.R;

@LayoutId(R.layout.reside_menu)
@LogTag("ResideMenuActivity")
@IsFullScreen(false)
public class ResideMenuActivity extends BaseFontActivity implements View.OnClickListener {
    private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMenu();
        if (savedInstanceState == null)
            changeFragment(new HomeFragment());
    }

    private void setupUIResideMenuItem(final ResideMenuItem resideMenuItem) {
        resideMenuItem.setTextColor(Color.BLACK);
        resideMenuItem.setTextShadow(Color.WHITE);
        resideMenuItem.setTextSize(getResources().getDimension(R.dimen.txt_medium));
        resideMenuItem.setIvIconSizeDp(20);
    }

    private void setUpMenu() {
        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.getRealtimeBlurView().setBlurRadius(50);
        resideMenu.getRealtimeBlurView().setOverlayColor(LAppResource.INSTANCE.getColor(R.color.black65));
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.iv);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.mipmap.ic_launcher, "Home");
        itemProfile = new ResideMenuItem(this, R.mipmap.ic_launcher, "Profile");
        itemCalendar = new ResideMenuItem(this, R.mipmap.ic_launcher, "Calendar");
        itemSettings = new ResideMenuItem(this, R.mipmap.ic_launcher, "Settings");

        setupUIResideMenuItem(itemHome);
        setupUIResideMenuItem(itemProfile);
        setupUIResideMenuItem(itemCalendar);
        setupUIResideMenuItem(itemSettings);

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        itemCalendar.setOnClickListener(this);
        itemSettings.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(view -> resideMenu.openMenu(ResideMenu.DIRECTION_LEFT));
        findViewById(R.id.title_bar_right_menu).setOnClickListener(view -> resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome) {
            changeFragment(new HomeFragment());
        } else if (view == itemProfile) {
            changeFragment(new ProfileFragment());
        } else if (view == itemCalendar) {
            changeFragment(new CalendarFragment());
        } else if (view == itemSettings) {
            changeFragment(new SettingsFragment());
        }

        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            showShort("Menu is opened!", true);
        }

        @Override
        public void closeMenu() {
            showShort("Menu is closed!", true);
        }
    };

    private void changeFragment(final Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }
}
