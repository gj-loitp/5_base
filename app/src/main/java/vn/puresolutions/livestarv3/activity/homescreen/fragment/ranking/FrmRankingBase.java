package vn.puresolutions.livestarv3.activity.homescreen.fragment.ranking;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.room.BaseFragmentForLOnlyViewPager;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmRankingBase extends BaseFragmentForLOnlyViewPager {
    private final String TAG = getClass().getSimpleName();
    private RankingAdapter rankingAdapter;
    private ViewPager viewPager;
    private final int MAX_SIZE_PAGE = 3;
    private final String titleList[] = {"Top yêu thích", "Top đại gia", "Top share Facebook"};
    private TabLayout tabLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_ranking_base, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        rankingAdapter = new RankingAdapter(getChildFragmentManager());
        viewPager.setAdapter(rankingAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //LUIUtil.fixSizeTabLayout(getActivity(), tabLayout, titleList);
        return view;
    }

    private class RankingAdapter extends FragmentStatePagerAdapter {

        public RankingAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmRankingPageTopFavourite();
                case 1:
                    return new FrmRankingPageTopRichMan();
                case 2:
                    return new FrmRankingPageTopShare();
            }
            return null;
        }

        @Override
        public int getCount() {
            return MAX_SIZE_PAGE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList[position];
        }
    }
}

