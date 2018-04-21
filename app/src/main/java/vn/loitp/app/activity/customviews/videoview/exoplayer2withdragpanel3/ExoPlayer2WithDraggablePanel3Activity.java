package vn.loitp.app.activity.customviews.videoview.exoplayer2withdragpanel3;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer.DrawerHeader;
import vn.loitp.app.activity.customviews.placeholderview.ex.androidnavigationdrawer.DrawerMenuItem;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LStoreUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.placeholderview.lib.placeholderview.PlaceHolderView;

public class ExoPlayer2WithDraggablePanel3Activity extends BaseActivity {
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

        changeFrm();
    }

    private void changeFrm() {
        FrmContainer fragment = new FrmContainer();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, fragment, fragment.toString());
        //fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
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
        return R.layout.activity_exo_player2_with_draggable_panel_3;
    }
}
