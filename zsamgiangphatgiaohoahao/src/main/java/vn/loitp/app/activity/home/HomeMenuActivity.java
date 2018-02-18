package vn.loitp.app.activity.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.ad.FrmGift;
import vn.loitp.app.activity.home.alllist.FrmAllList;
import vn.loitp.app.activity.home.more.FrmMore;
import vn.loitp.app.app.LSApplication;
import vn.loitp.app.common.Constants;
import vn.loitp.app.data.DataManager;
import vn.loitp.app.model.Category;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LAppBarLayout;

public class HomeMenuActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<Category> categoryList = new ArrayList<>();
    private ImageView toolbarImage;
    private DataManager dataManager;
    //private Handler handlerSearch = new Handler();

    private final int POS_PAGE_GIFT = 5;

    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataManager = new DataManager(activity);
        try {
            dataManager.createDatabase();
            LLog.d(TAG, "init dtb success");
        } catch (IOException e) {
            LLog.d(TAG, "init dtb failed: " + e.toString());
            showDialogError(getString(R.string.err_unknow));
            return;
        }

        isShowAdWhenExist = false;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbarImage = (ImageView) findViewById(R.id.toolbar_image);

        AppUtil.loadBackground(activity, toolbarImage);

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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
                    //LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    //LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                    AppUtil.loadBackground(activity, toolbarImage);
                } else {
                    //IDLE appBarLayout not min not max
                    //LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });

        categoryList = dataManager.getAllCategory(DataManager.TABLE_NAME_CATEGORY);
        categoryList.remove(categoryList.size() - 1);
        //LLog.d(TAG, "categoryList " + LSApplication.getInstance().getGson().toJson(categoryList));

        //add item gift and more
        Category categoryGift = new Category();
        categoryGift.setDescription(Constants.MENU_GIFT);
        categoryList.add(POS_PAGE_GIFT, categoryGift);

        Category categoryMore = new Category();
        categoryMore.setDescription(Constants.MENU_MORE);
        categoryList.add(categoryMore);

        findViewById(R.id.bt_menu).setOnClickListener(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_home_menu;
    }

    private FrmAllList genFrmAllList(int pos) {
        FrmAllList frmAllList = new FrmAllList();
        Bundle bundle = new Bundle();
        Category category = categoryList.get(pos);
        bundle.putSerializable(Constants.MENU_CATEGORY, category);
        frmAllList.setArguments(bundle);
        return frmAllList;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == POS_PAGE_GIFT) {
                return new FrmGift();
            } else if (position == categoryList.size() - 1) {
                return new FrmMore();
            } else {
                return genFrmAllList(position);
            }
        }

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoryList.get(position).getDescription();
        }
    }

    private boolean isExit;

    @Override
    public void onBackPressed() {
        if (isExit) {
            super.onBackPressed();
        } else {
            LDialogUtil.showDialog3(activity, getString(R.string.app_name), getString(R.string.msg_exit_app), getString(R.string.yes), getString(R.string.no), getString(R.string.rate), new LDialogUtil.Callback3() {
                @Override
                public void onClick1() {
                    isExit = true;
                    onBackPressed();
                }

                @Override
                public void onClick2() {
                    //do nothing
                }

                @Override
                public void onClick3() {
                    LSocialUtil.rateApp(activity, getPackageName());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu:
                LPopupMenu.show(activity, v, R.menu.menu_popup, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.rate_app:
                                LSocialUtil.rateApp(activity, getPackageName());
                                break;
                            case R.id.more_app:
                                LSocialUtil.moreApp(activity);
                                break;
                            case R.id.share_app:
                                LSocialUtil.shareApp(activity);
                                break;
                            case R.id.like_facebook_fanpage:
                                LSocialUtil.likeFacebookFanpage(activity);
                                break;
                            case R.id.support_24_7:
                                LSocialUtil.chatMessenger(activity);
                                break;
                        }
                    }
                });
                break;
        }
    }
}
