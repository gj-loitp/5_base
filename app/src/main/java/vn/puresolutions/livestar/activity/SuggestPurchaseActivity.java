package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * @author hoangphu
 * @since 12/28/16
 */

public class SuggestPurchaseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        overridePendingTransition(R.anim.enter_up, R.anim.exit_up);
    }

    @OnClick(R.id.suggestPurchaseDialog_btnPositive)
    void purchaseCoin() {
        startActivity(new Intent(this, PurchaseActivity.class));
        finish();
    }

    @OnClick({R.id.suggestPurchaseDialog_btnNegative,
            R.id.suggestPurchaseDialog_imgClose})
    void cancel() {
        finish();
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
        return R.layout.dialog_suggest_purchase;
    }
}
