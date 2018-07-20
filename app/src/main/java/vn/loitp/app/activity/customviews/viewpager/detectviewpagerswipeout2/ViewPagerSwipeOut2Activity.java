package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import loitp.basemaster.R;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.AutoViewPagerActivity;
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv;
import vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout.ex.DetectViewPagerSwipeOutActivity;
import vn.loitp.app.activity.customviews.viewpager.doubleviewpager.DoubleViewPagerSplashActivity;
import vn.loitp.app.activity.customviews.viewpager.parrallaxviewpager.ParallaxViewPagerActivity;
import vn.loitp.app.activity.customviews.viewpager.viewpagerwithtablayout.ViewPagerWithTabLayoutActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;

public class ViewPagerSwipeOut2Activity extends BaseFontActivity {
    private SwipeOutViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (SwipeOutViewPager) findViewById(R.id.vp);

        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        viewPager.setOnSwipeOutListener(new SwipeOutViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {
                LToast.show(activity, "onSwipeOutAtStart");
            }

            @Override
            public void onSwipeOutAtEnd() {
                LToast.show(activity, "onSwipeOutAtEnd");
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.changeTabsFont(tabLayout, vn.loitp.core.common.Constants.FONT_PATH);
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
        return R.layout.activity_view_pager_swipe_out_2;
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter {

        SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FrmIv.newInstance();
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page Title " + position;
        }
    }
}
