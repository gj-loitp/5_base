package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.google.android.material.tabs.TabLayout;
import com.views.LToast;
import com.views.viewpager.swipeoutviewpager.SwipeOutViewPager;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv;

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
                LToast.INSTANCE.show(getActivity(), "onSwipeOutAtStart");
            }

            @Override
            public void onSwipeOutAtEnd() {
                LToast.INSTANCE.show(getActivity(), "onSwipeOutAtEnd");
            }
        });

        LUIUtil.INSTANCE.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.INSTANCE.changeTabsFont(tabLayout, com.core.common.Constants.INSTANCE.getFONT_PATH());
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
