package vn.loitp.app.activity.ads;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.ads.admobbanner.AdmobBannerActivity;
import vn.loitp.app.activity.ads.admobinterstitial.AdmobInterstitialActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.loitp.admobrewardedvideo.AdmobRewardedVideoActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class MenuAdsActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        findViewById(R.id.bt_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AdmobBannerActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AdmobInterstitialActivity.class);
                startActivity(intent);
                LActivityUtil.tranIn(activity);
            }
        });
        findViewById(R.id.bt_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, AdmobRewardedVideoActivity.class);
                intent.putExtra(AdmobRewardedVideoActivity.APP_ID, getString(R.string.str_app_id));
                intent.putExtra(AdmobRewardedVideoActivity.ID_REWARD, getString(R.string.str_reward));
                startActivity(intent);
                LActivityUtil.tranIn(activity);
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
        return R.layout.activity_menu_ads;
    }
}
