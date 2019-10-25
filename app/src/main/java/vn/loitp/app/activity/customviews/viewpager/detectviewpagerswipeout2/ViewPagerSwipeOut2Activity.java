package vn.loitp.app.activity.customviews.viewpager.detectviewpagerswipeout2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.google.android.material.tabs.TabLayout;
import com.views.LToast;
import com.views.viewpager.swipeout.LSwipeOutViewPager;

import org.jetbrains.annotations.NotNull;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv;

public class ViewPagerSwipeOut2Activity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LSwipeOutViewPager viewPager = findViewById(R.id.vp);

        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        viewPager.setOnSwipeOutListener(new LSwipeOutViewPager.OnSwipeOutListener() {
            @Override
            public void onSwipeOutAtStart() {
                LToast.show(getActivity(), "onSwipeOutAtStart");
            }

            @Override
            public void onSwipeOutAtEnd() {
                LToast.show(getActivity(), "onSwipeOutAtEnd");
            }
        });

        LUIUtil.INSTANCE.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
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
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return FrmIv.Companion.newInstance();
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
