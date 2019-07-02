package vn.loitp.app.activity.customviews.actionbar.collapsingtoolbarlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.views.LAppBarLayout;
import vn.loitp.views.LToast;

public class CollapsingToolbarLayoutActivity extends BaseFontActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(LAppBarLayout.State toolbarChange) {
                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
                    //COLLAPSED appBarLayout min
                    LLog.INSTANCE.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    LLog.INSTANCE.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                } else {
                    //IDLE appBarLayout not min not max
                    LLog.INSTANCE.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        findViewById(R.id.bt_menu).setOnClickListener(this);
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
        return R.layout.activity_collapsingtoolbar;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu:
                LPopupMenu.show(activity, v, R.menu.menu_popup, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        LToast.INSTANCE.show(activity, menuItem.getTitle().toString());
                    }
                });
                break;
        }
    }
}
