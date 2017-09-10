package vn.puresolutions.livestarv3.activity.homescreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign.GetCoinPagerActivity;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

public class GoToPrivateRoomNoCoinActivity extends BaseActivity {
    private LActionBar lActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViews();
        setupActionBar();
        findViewById(R.id.tv_get_coin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTvGetCoin();
            }
        });
    }

    private void setupActionBar() {
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
        lActionBar.setImageBackIcon(R.drawable.back_white);
        lActionBar.setTvTitle("");
        lActionBar.hideMenuIcon();
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
        return R.layout.activity_go_to_private_room_no_coin;
    }

    private void findViews() {
        lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
    }

    public void onClickTvGetCoin() {
        Intent intent = new Intent(activity, GetCoinPagerActivity.class);
        startActivity(intent);
        LUIUtil.transActivityFadeIn(activity);
        finish();
    }
}
