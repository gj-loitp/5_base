package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.fragment.PurchaseFragment;
import vn.puresolutions.livestar.helper.IABHelper;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * @author hoangphu
 * @since 11/27/16
 */

public class PurchaseActivity extends BaseActivity {
    public static final String SELECT_PURCHASE_COIN_TAB_EXTRA = "SELECT_PURCHASE_COIN_TAB_EXTRA";

    @BindView(R.id.purchaseActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.purchaseActivity_tvTitle)
    TextView tvTitle;

    private IABHelper iabHelper;
    private PurchaseFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fragment = (PurchaseFragment) getSupportFragmentManager().findFragmentById(R.id.purchaseActivity_frgPurchase);
        iabHelper = new IABHelper(this);

        boolean selectCoinTab = getIntent().getBooleanExtra(SELECT_PURCHASE_COIN_TAB_EXTRA, false);
        if (selectCoinTab) {
            switchToCoinPurchaseFragment();
        }
    }

    public IABHelper getIABHelper() {
        return iabHelper;
    }

    public void switchToCoinPurchaseFragment() {
        fragment.switchToCoinPurchaseFragment();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
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
        return R.layout.activity_purchase;
    }
}
