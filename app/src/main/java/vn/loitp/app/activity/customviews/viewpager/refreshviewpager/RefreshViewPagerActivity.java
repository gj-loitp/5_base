package vn.loitp.app.activity.customviews.viewpager.refreshviewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import vn.loitp.app.R;

public class RefreshViewPagerActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = findViewById(R.id.vp);
        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
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
        return R.layout.activity_refresh_viewpager;
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter {

        SamplePagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            FrmRefresh frmRefresh = new FrmRefresh();
            Bundle bundle = new Bundle();
            bundle.putInt(FrmRefresh.KEY_POSITION, position);
            frmRefresh.setArguments(bundle);
            return frmRefresh;
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
