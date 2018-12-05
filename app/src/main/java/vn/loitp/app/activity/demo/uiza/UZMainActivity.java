package vn.loitp.app.activity.demo.uiza;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.restapi.uiza.model.v3.metadata.getlistmetadata.ResultGetListMetadata;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class UZMainActivity extends BaseFontActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private AVLoadingIndicatorView avl;
    private List<Data> metadataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
            }

            @Override
            public void onDrawerStateChanged(int i) {
            }
        });*/
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        avl = (AVLoadingIndicatorView) findViewById(R.id.avl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                LLog.d(TAG, "onNavigationItemSelected " + item.getTitle() + ", isCheckable: " + item.isCheckable());
                UZD.getInstance().setMetadata(getMetadata(item));
                switchPage();
                drawerLayout.closeDrawers();
                return true;
            }
        });
        LUIUtil.setNavMenuItemThemeColors(navigationView, Color.GRAY, Color.BLACK);
        getListMetadata();
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_uiza_main;
    }

    private void getListMetadata() {
        UZService service = UZRestClient.createService(UZService.class);
        subscribe(service.getListMetadata(), new ApiSubscriber<ResultGetListMetadata>() {
            @Override
            public void onSuccess(ResultGetListMetadata resultGetListMetadata) {
                LLog.d(TAG, "getListMetadata onSuccess: " + LSApplication.getInstance().getGson().toJson(resultGetListMetadata));
                if (resultGetListMetadata == null) {
                    return;
                }
                metadataList = resultGetListMetadata.getData();
                if (navigationView == null) {
                    return;
                }
                final Menu menu = navigationView.getMenu();
                for (int i = 0; i < metadataList.size(); i++) {
                    MenuItem menuItem = menu.add(metadataList.get(i).getName());
                    menuItem.setCheckable(true);
                    if (i == 0) {
                        UZD.getInstance().setMetadata(metadataList.get(0));
                        menuItem.setChecked(true);
                        switchPage();
                    }
                }
                hideAvl();
            }

            @Override
            public void onFail(Throwable e) {
                LLog.e(TAG, "getListMetadata onFail " + e.getMessage());
                hideAvl();
            }
        });
    }

    public void switchPage() {
        FrmEntity frmEntity = new FrmEntity();
        LScreenUtil.replaceFragment(activity, R.id.fl_container, frmEntity, false);
    }

    public void showAvl() {
        if (avl != null) {
            avl.smoothToShow();
        }
    }

    public void hideAvl() {
        if (avl != null) {
            avl.smoothToHide();
        }
    }

    private Data getMetadata(MenuItem menuItem) {
        if (menuItem == null || metadataList == null) {
            return null;
        }
        for (int i = 0; i < metadataList.size(); i++) {
            if (menuItem.getTitle().equals(metadataList.get(i).getName())) {
                return metadataList.get(i);
            }
        }
        return null;
    }
}
