package vn.loitp.app.activity.customviews.viewpager.refreshviewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LUIUtil;

public class RefreshViewPagerActivity extends BaseFontActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        LUIUtil.setPullLikeIOSHorizontal(viewPager);
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
        return R.layout.activity_refresh_viewpager;
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter {

        SamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

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
