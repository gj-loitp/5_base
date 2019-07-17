package vn.loitp.app.activity.customviews.viewpager.lockableviewpager;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.google.android.material.tabs.TabLayout;
import com.views.viewpager.lockableviewpager.LockableViewPager;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.viewpager.autoviewpager.FrmIv;

public class LockableViewPagerActivity extends BaseFontActivity {
    private LockableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (LockableViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(new SamplePagerAdapter(getSupportFragmentManager()));
        //LUIUtil.setPullLikeIOSHorizontal(viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        LUIUtil.changeTabsFont(tabLayout, com.core.common.Constants.INSTANCE.getFONT_PATH());

        findViewById(R.id.bt_enable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setSwipeLocked(false);
            }
        });
        findViewById(R.id.bt_disable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setSwipeLocked(true);
            }
        });
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
        return R.layout.activity_lockable_viewpager;
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
