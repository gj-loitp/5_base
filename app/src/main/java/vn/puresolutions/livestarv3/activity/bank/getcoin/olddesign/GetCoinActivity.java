package vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;
import vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.card.GetCoinByCardActivity;
import vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.internetbanking.GetCoinByInternetBankingActivity;
import vn.puresolutions.livestarv3.activity.bank.getcoin.olddesign.sms.GetCoinBySMSActivity;
import vn.puresolutions.livestarv3.view.LActionBar;

public class GetCoinActivity extends BaseActivity {
    private LActionBar lActionBar;
    private LinearLayout viewSms;
    private LinearLayout viewCard;
    private LinearLayout viewInternetBanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();

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
        lActionBar.setTvTitle(getString(R.string.get_coin_));

        viewSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GetCoinBySMSActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        viewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GetCoinByCardActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
            }
        });
        viewInternetBanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GetCoinByInternetBankingActivity.class);
                startActivity(intent);
                LUIUtil.transActivityFadeIn(activity);
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
        return R.layout.activity_get_coin;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        viewSms = (LinearLayout) findViewById(R.id.view_sms);
        viewCard = (LinearLayout) findViewById(R.id.view_card);
        viewInternetBanking = (LinearLayout) findViewById(R.id.view_internet_banking);
    }
}
