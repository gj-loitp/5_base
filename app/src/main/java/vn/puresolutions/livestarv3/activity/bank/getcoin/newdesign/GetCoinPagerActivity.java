package vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.bank.getcoin.IABHelper;
import vn.puresolutions.livestarv3.activity.bank.manager.FrmBankHistoryGift;
import vn.puresolutions.livestarv3.activity.bank.manager.FrmBankHistorySpend;
import vn.puresolutions.livestarv3.base.BaseActivity;

public class GetCoinPagerActivity extends BaseActivity {
    private IABHelper iabHelper;
    private CoinManagerAdapter coinManagerAdapter;
    private ViewPager viewPager;
    private final int MAX_SIZE_PAGE = 2;
    private final String titleList[] = {"Nạp Xu", "Hình thức khác"};
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iabHelper = new IABHelper(this);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        coinManagerAdapter = new CoinManagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(coinManagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //LUIUtil.fixSizeTabLayout(getActivity(), tabLayout, titleList);

        findViewById(R.id.iv_icon_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_get_coin_pager;
    }

    private class CoinManagerAdapter extends FragmentPagerAdapter {

        public CoinManagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmCoinNative();
                case 1:
                    return new FrmCoinWeb();
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

    public IABHelper getIABHelper() {
        return iabHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iabHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iabHelper.onDestroy();
    }
}
