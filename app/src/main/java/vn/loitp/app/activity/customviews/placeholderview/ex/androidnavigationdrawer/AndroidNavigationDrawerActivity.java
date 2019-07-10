package vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.core.base.BaseFontActivity;
import com.core.utilities.LStoreUtil;
import com.core.utilities.LUIUtil;

import loitp.basemaster.R;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;

public class AndroidNavigationDrawerActivity extends BaseFontActivity {
    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView) findViewById(R.id.drawerView);

        LUIUtil.setPullLikeIOSVertical(mDrawerView);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setupDrawer();

        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(LStoreUtil.readTxtFromRawFolder(activity, R.raw.loitp));
    }

    private void setupDrawer() {
        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()))
                .addView(new DrawerMenuItem(this.getApplicationContext()));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_android_navigation_drawer;
    }
}
