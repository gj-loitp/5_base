package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.fragment.RankCoinFragment;
import vn.puresolutions.livestar.fragment.RankHeartFragment;
import vn.puresolutions.livestar.fragment.RankShareFragment;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * File created on 6/29/2017.
 *
 * @author Anhdv
 */
public class RankActivity extends BaseActivity {
    RankViewPagerAdapter rank_adapter;
    @BindView(R.id.rankActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tlRank)
    TabLayout tabRank;
    @BindView(R.id.vpRank)
    ViewPager vpRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        rank_adapter = new RankViewPagerAdapter(getSupportFragmentManager());
        setupViewPager(vpRank);
        vpRank.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("RankActivity", "position-> " + position);
                vpRank.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabRank.setupWithViewPager(vpRank);
    }

    private void setupViewPager(ViewPager viewPager) {
        //adapter = new ViewPagerAdapter(getSupportFragmentManager());
        rank_adapter.addFragment(new RankHeartFragment(), "Top Yêu Thích");
        rank_adapter.addFragment(new RankCoinFragment(), "Top Đại Gia");
        rank_adapter.addFragment(new RankShareFragment(), "Top Share Facebook");
        viewPager.setAdapter(rank_adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    public class RankViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public RankViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

       /* @Override
        public int getItemPosition(Object object) {
            return FragmentPagerAdapter.POSITION_NONE;
        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

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
        return R.layout.activity_rank;
    }
}
