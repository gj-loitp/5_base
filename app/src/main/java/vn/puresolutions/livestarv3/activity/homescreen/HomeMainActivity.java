package vn.puresolutions.livestarv3.activity.homescreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.socket.emitter.Emitter;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.homenew.FrmHomeNew;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.livestream.FrmLivestream;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.profile.FrmProfile;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking.FrmRankingBase;
import vn.puresolutions.livestarv3.activity.homescreen.fragment.search.FrmSearch;
import vn.puresolutions.livestarv3.activity.livestream.LivestreamActivity;
import vn.puresolutions.livestarv3.activity.login.LoginActivity;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.AlertMessage;
import vn.puresolutions.livestarv3.utilities.v3.LLog;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.utilities.v3.NotifySocketHelper;
import vn.puresolutions.livestarv3.view.LBottomBar;
import vn.puresolutions.livestarv3.view.LScrollableViewPager;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */
public class HomeMainActivity extends BaseActivity {
    private LBottomBar lBottomBar;
    private final int SIZE_PAGE = 4;
    private ViewPagerAdapter viewPagerAdapter;
    private LScrollableViewPager lScrollableViewPager;
    private boolean isPausedActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lBottomBar = (LBottomBar) findViewById(R.id.bottom_bar);
        lScrollableViewPager = (LScrollableViewPager) findViewById(R.id.view_pager);
        lScrollableViewPager.setCanScroll(false);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        lScrollableViewPager.setAdapter(viewPagerAdapter);
        lScrollableViewPager.setOffscreenPageLimit(SIZE_PAGE);

        lBottomBar.setOnItemClick(new LBottomBar.Callback() {
            @Override
            public void OnClickItem(int position) {
                if (position == LBottomBar.PAGE_LIVESTREAM) {
                    if (!LPref.isUserLoggedIn(activity)) {
                        Intent intent = new Intent(activity, LoginActivity.class);
                        intent.putExtra(Constants.LOGIN_IS_CALL_FROM_HOME_MAIN_LIVE_STREAM, true);
                        startActivity(intent);
                        LUIUtil.transActivityFadeIn(activity);
                    } else {
                        Intent intent = new Intent(activity, LivestreamActivity.class);
                        startActivity(intent);
                        LUIUtil.transActivityFadeIn(activity);
                    }
                    lBottomBar.setCurrentPos(LBottomBar.PAGE_NONE);
                } else if (position == LBottomBar.PAGE_PROFILE && !LPref.isUserLoggedIn(activity)) {
                    Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                    LUIUtil.transActivityFadeIn(activity);
                    lBottomBar.setCurrentPos(LBottomBar.PAGE_NONE);
                } else {
                    lScrollableViewPager.setCurrentItem(position, true);
                }
            }
        });

        /*lScrollableViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //do nothing
            }

            @Override
            public void onPageSelected(int position) {
                lBottomBar.setPerformItemClick(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //do nothing
            }
        });*/
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
        return R.layout.activity_home_main_screen;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case LBottomBar.PAGE_HOME:
                    return new FrmHomeNew();
                case LBottomBar.PAGE_SEARCH:
                    return new FrmSearch();
                case LBottomBar.PAGE_LIVESTREAM:
                    return new FrmLivestream();
                case LBottomBar.PAGE_RANKING:
                    return new FrmRankingBase();
                case LBottomBar.PAGE_PROFILE:
                    return new FrmProfile();
            }
            return null;
        }

        @Override
        public int getCount() {
            return SIZE_PAGE;
        }
    }

    public void scrollToPage(int position) {
        if (position >= 0 && position < SIZE_PAGE) {
            Handler uiHandler = new Handler();
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    lBottomBar.setPerformItemClick(position);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPausedActivity = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPausedActivity = false;
        if (LPref.isUserLoggedIn(activity)) {
            NotifySocketHelper.getInstance().open(LPref.getToken(activity), new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    LLog.d(TAG, "Socket Notifiaction connected");
                }
            });
        }
    }

    public boolean isPausedActivity() {
        return isPausedActivity;
    }

    //double tap to exit
    private boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        AlertMessage.showSuccess(activity, getString(R.string.press_back_again_to_exit_app));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
