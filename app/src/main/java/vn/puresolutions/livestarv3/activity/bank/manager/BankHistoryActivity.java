package vn.puresolutions.livestarv3.activity.bank.manager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign.GetCoinPagerActivity;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class BankHistoryActivity extends BaseActivity {
    private LActionBar lActionBar;
    private BankHistoryAdapter bankHistoryAdapter;
    private ViewPager viewPager;
    private final int MAX_SIZE_PAGE = 2;
    private final String titleList[] = {"Lịch sử chi tiêu", "Quà tặng được nhận"};
    private TabLayout tabLayout;
    private TextView tvGetCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        tvGetCoin = (TextView) findViewById(R.id.tv_get_coin);
        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });
        lActionBar.setTvTitle(getString(R.string.coin_manager));

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        bankHistoryAdapter = new BankHistoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bankHistoryAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //LUIUtil.fixSizeTabLayout(getActivity(), tabLayout, titleList);
        tvGetCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GetCoinPagerActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });

        TextView tvCurrentCoin = (TextView) findViewById(R.id.tv_current_coin);
        UserProfile userProfile = LPref.getUser(activity);
        if (userProfile != null) {
            tvCurrentCoin.setText(String.valueOf(userProfile.getMoney()));
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
        return R.layout.activity_bank_history;
    }

    private class BankHistoryAdapter extends FragmentPagerAdapter {

        public BankHistoryAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmBankHistorySpend();
                case 1:
                    return new FrmBankHistoryGift();
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
