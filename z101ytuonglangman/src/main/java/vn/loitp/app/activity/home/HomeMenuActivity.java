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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.ad.FrmPhotoGift;
import vn.loitp.app.activity.home.more.FrmMore;
import vn.loitp.app.common.Constants;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LKeyBoardUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LAppBarLayout;

public class HomeMenuActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private ImageView toolbarImage;
    private EditText etSearch;
    private TextView tvMsgSearch;

    //private Handler handlerSearch = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbarImage = (ImageView) findViewById(R.id.toolbar_image);
        etSearch = (EditText) findViewById(R.id.et_search);
        tvMsgSearch = (TextView) findViewById(R.id.tv_msg_search);
        tvMsgSearch.setOnClickListener(this);
        LUIUtil.setMarquee(tvMsgSearch);

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

        stringList.add(Constants.MENU_ALL_LIST);
        stringList.add(Constants.MENU_FAV_LIST);
        stringList.add(Constants.MENU_GIFT);
        stringList.add(Constants.MENU_MORE);

        findViewById(R.id.bt_menu).setOnClickListener(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LLog.d(TAG, "onTextChanged " + s);
                handlerSearch.removeCallbacksAndMessages(null);
                handlerSearch.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LLog.d(TAG, ">>>>>>>>>>>send event search >>> " + s);
                        EventBusData.getInstance().sendSearchEvent(s.toString(), stringList.get(viewPager.getCurrentItem()));
                    }
                }, 1500);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });*/

        LUIUtil.setImeiActionSearch(etSearch, new LUIUtil.CallbackSearch() {
            @Override
            public void onSearch() {
                LLog.d(TAG, "onSearch");
                if (etSearch.getText().toString().isEmpty()) {
                    isShowTvMsgSearch = false;
                } else {
                    isShowTvMsgSearch = true;
                }
                LKeyBoardUtil.hide(activity);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                tvMsgSearch.performClick();
                if (position == 0 || position == 1 || position == 2) {
                    showEtSearch(true);
                } else {
                    showEtSearch(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });
    }

    public String getCurrentSearchKeyword() {
        return etSearch.getText().toString().trim().toLowerCase();
    }

    public void showEtSearch(boolean isShow) {
        if (etSearch != null) {
            if (isShow) {
                if (etSearch.getVisibility() != View.VISIBLE) {
                    etSearch.setVisibility(View.VISIBLE);
                    LAnimationUtil.play(etSearch, Techniques.FadeIn);
                }
            } else {
                if (etSearch.getVisibility() != View.GONE) {
                    LAnimationUtil.play(etSearch, Techniques.FadeOut, new LAnimationUtil.Callback() {
                        @Override
                        public void onCancel() {
                            //do nothing
                        }

                        @Override
                        public void onEnd() {
                            etSearch.setVisibility(View.GONE);
                        }

                        @Override
                        public void onRepeat() {
                            //do nothing
                        }

                        @Override
                        public void onStart() {
                            //do nothing
                        }
                    });
                }
            }
        }
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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmMore();
                case 1:
                    return new FrmMore();
                case 2:
                    return new FrmPhotoGift();
                case 3:
                    return new FrmMore();
            }
            return null;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
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
            case R.id.tv_msg_search:
                isShowTvMsgSearch = false;
                etSearch.setText("");
                break;
        }
    }

    /*@Override
    protected void onNetworkChange(EventBusData.ConnectEvent event) {
        super.onNetworkChange(event);
        LLog.d(TAG, "onMessageEvent loitp: " + event.isConnected());
    }*/

    private boolean isShowTvMsgSearch;

    public void setSearchCount(int searchCount) {
        LLog.d(TAG, "setSearchCount");
        tvMsgSearch.setText("Tìm được " + searchCount + " kết quả với từ khóa " + etSearch.getText().toString().toUpperCase() + ". Nhấn vào đây để tắt tìm kiếm");
        if (isShowTvMsgSearch) {
            if (tvMsgSearch.getVisibility() != View.VISIBLE) {
                LLog.d(TAG, "tvMsgSearch VISIBLE");
                tvMsgSearch.setVisibility(View.VISIBLE);
            }
        }
    }
}
