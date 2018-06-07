package vn.loitp.app.activity.customviews.viewpager.autoviewpager;

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
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.app.common.Constants;
import vn.loitp.views.viewpager.autoviewpager.lib.AutoViewPager;

public class AutoViewPagerActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        //viewPager.setIndeterminate(true);
        viewPager.setAutoScrollEnabled(true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        changeTabsFont(tabLayout, Constants.FONT_PATH);
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
        return R.layout.activity_auto_viewpager;
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

    private void changeTabsFont(TabLayout tabLayout, String fontName) {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    CalligraphyUtils.applyFontToTextView(tabLayout.getContext(), (TextView) tabViewChild, fontName);
                }
            }
        }
    }
}