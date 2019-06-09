package vn.loitp.app.activity.demo.uiza;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.app.LSApplication;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.adhelper.AdHelperActivity;
import vn.loitp.core.loitp.uiza.FrmEntity;
import vn.loitp.core.loitp.uiza.UZCons;
import vn.loitp.core.loitp.uiza.UZD;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.uiza.UZRestClient;
import vn.loitp.restapi.uiza.UZService;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;
import vn.loitp.restapi.uiza.model.v3.metadata.getlistmetadata.ResultGetListMetadata;
import vn.loitp.rxandroid.ApiSubscriber;
import vn.loitp.views.actionbar.LActionBar;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class UZMainActivity extends BaseFontActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private AVLoadingIndicatorView avl;
    private List<Data> metadataList;
    private LActionBar lActionBar;

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
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        LUIUtil.setTextShadow(lActionBar.getTvTitle(), Color.WHITE);
        lActionBar.showMenuIcon();
        lActionBar.setImageBackIcon(R.drawable.ic_more_vert_white_48dp);
        lActionBar.setImageMenuIcon(R.drawable.ic_more_vert_white_48dp);
        lActionBar.getIvIconBack().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary));
        lActionBar.getIvIconMenu().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary));
        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                if (drawerLayout != null) {
                    drawerLayout.openDrawer(Gravity.START);
                }
            }

            @Override
            public void onClickMenu() {
                showMenu();
            }
        });
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        avl = (AVLoadingIndicatorView) findViewById(R.id.avl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //LLog.d(TAG, "onNavigationItemSelected " + item.getTitle() + ", isCheckable: " + item.isCheckable());
                UZD.getInstance().setMetadata(getMetadata(item));
                switchPage();
                drawerLayout.closeDrawers();
                return true;
            }
        });
        LUIUtil.setNavMenuItemThemeColors(navigationView, Color.GRAY, Color.BLACK);
        getListMetadata();
    }

    private void showMenu() {
        LPopupMenu.show(activity, lActionBar.getIvIconMenu(), R.menu.menu_uz, new LPopupMenu.CallBack() {
            @Override
            public void clickOnItem(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_rate:
                        LSocialUtil.rateApp(activity, getPackageName());
                        break;
                    case R.id.action_more:
                        LSocialUtil.moreApp(activity);
                        break;
                    case R.id.action_share:
                        LSocialUtil.shareApp(activity);
                        break;
                    case R.id.action_like_fb:
                        LSocialUtil.likeFacebookFanpage(activity);
                        break;
                    case R.id.bt_support_24_7:
                        LSocialUtil.chatMessenger(activity);
                        break;
                    case R.id.action_why_u_see_ad:
                        Intent intent = new Intent(activity, AdHelperActivity.class);
                        intent.putExtra(Constants.INSTANCE.getAD_HELPER_IS_ENGLISH_LANGUAGE(), false);
                        startActivity(intent);
                        LActivityUtil.INSTANCE.tranIn(activity);
                        break;
                    case R.id.action_policy:
                        LSocialUtil.openBrowserPolicy(activity);
                        break;
                }
            }
        });
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
                LLog.INSTANCE.d(TAG, "getListMetadata onSuccess: " + LSApplication.Companion.getGson().toJson(resultGetListMetadata));
                if (resultGetListMetadata == null) {
                    return;
                }
                if (navigationView == null) {
                    return;
                }
                final Menu menu = navigationView.getMenu();
                metadataList = resultGetListMetadata.getData();
                //add menuItem Trang chu
                Data home = new Data();
                home.setName(UZCons.HOME);
                metadataList.add(0, home);

                for (int i = 0; i < metadataList.size(); i++) {
                    MenuItem menuItem = menu.add(metadataList.get(i).getName());
                    menuItem.setCheckable(true);
                    if (i == 0) {
                        UZD.getInstance().setMetadata(metadataList.get(0));
                        menuItem.setChecked(true);
                        switchPage();
                    }
                }
                avl.smoothToHide();
            }

            @Override
            public void onFail(Throwable e) {
                LLog.INSTANCE.e(TAG, "getListMetadata onFail " + e.getMessage());
                avl.smoothToHide();
            }
        });
    }

    private void setTvTilte(String title) {
        lActionBar.setTvTitle(title);
    }

    public void switchPage() {
        setTvTilte(UZD.getInstance().getMetadata().getName());
        FrmEntity frmEntity = new FrmEntity();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INSTANCE.getAD_UNIT_ID_BANNER(), getString(R.string.str_b));
        bundle.putBoolean(Constants.INSTANCE.getIS_HIDE_SPACE_VIEW(), false);
        frmEntity.setArguments(bundle);
        LScreenUtil.replaceFragment(activity, R.id.fl_container, frmEntity, false);
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
