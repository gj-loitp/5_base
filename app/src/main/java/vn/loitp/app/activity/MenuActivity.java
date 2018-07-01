package vn.loitp.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.activity.ads.MenuAdsActivity;
import vn.loitp.app.activity.animation.MenuAnimationActivity;
import vn.loitp.app.activity.api.MenuAPIActivity;
import vn.loitp.app.activity.customviews.MenuCustomViewsActivity;
import vn.loitp.app.activity.database.MenuDatabaseActivity;
import vn.loitp.app.activity.demo.MenuDemoActivity;
import vn.loitp.app.activity.function.MenuFunctionActivity;
import vn.loitp.app.activity.more.MoreActivity;
import vn.loitp.app.activity.pattern.MenuPatternActivity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.adhelper.AdHelperActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;

public class MenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;

        TextView tvPolicy = (TextView) findViewById(R.id.tv_policy);
        LUIUtil.setTextShadow(tvPolicy);
        tvPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LSocialUtil.openUrlInBrowser(activity, Constants.URL_POLICY);
            }
        });

        findViewById(R.id.bt_api).setOnClickListener(this);
        findViewById(R.id.bt_animation).setOnClickListener(this);
        findViewById(R.id.bt_custom_view).setOnClickListener(this);
        findViewById(R.id.bt_demo).setOnClickListener(this);
        findViewById(R.id.bt_function).setOnClickListener(this);
        findViewById(R.id.bt_ads).setOnClickListener(this);
        findViewById(R.id.bt_rate_app).setOnClickListener(this);
        findViewById(R.id.bt_more_app).setOnClickListener(this);
        findViewById(R.id.bt_database).setOnClickListener(this);
        findViewById(R.id.bt_pattern).setOnClickListener(this);
        findViewById(R.id.bt_chat).setOnClickListener(this);
        findViewById(R.id.bt_github).setOnClickListener(this);
        findViewById(R.id.bt_ad_helper).setOnClickListener(this);
        findViewById(R.id.bt_fb_fanpage).setOnClickListener(this);
        findViewById(R.id.bt_frm_more).setOnClickListener(this);
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
        return R.layout.activity_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_api:
                intent = new Intent(activity, MenuAPIActivity.class);
                break;
            case R.id.bt_animation:
                intent = new Intent(activity, MenuAnimationActivity.class);
                break;
            case R.id.bt_custom_view:
                intent = new Intent(activity, MenuCustomViewsActivity.class);
                break;
            case R.id.bt_demo:
                intent = new Intent(activity, MenuDemoActivity.class);
                break;
            case R.id.bt_ads:
                intent = new Intent(activity, MenuAdsActivity.class);
                break;
            case R.id.bt_rate_app:
                LSocialUtil.rateApp(activity, getPackageName());
                break;
            case R.id.bt_more_app:
                LSocialUtil.moreApp(activity);
                break;
            case R.id.bt_function:
                intent = new Intent(activity, MenuFunctionActivity.class);
                break;
            case R.id.bt_database:
                intent = new Intent(activity, MenuDatabaseActivity.class);
                break;
            case R.id.bt_pattern:
                intent = new Intent(activity, MenuPatternActivity.class);
                break;
            case R.id.bt_chat:
                LSocialUtil.chatMessenger(activity);
                break;
            case R.id.bt_github:
                LSocialUtil.openUrlInBrowser(activity, "https://github.com/tplloi/basemaster");
                break;
            case R.id.bt_ad_helper:
                intent = new Intent(activity, AdHelperActivity.class);
                intent.putExtra(Constants.AD_HELPER_IS_ENGLISH_LANGUAGE, true);
                break;
            case R.id.bt_fb_fanpage:
                LSocialUtil.likeFacebookFanpage(activity);
                break;
            case R.id.bt_frm_more:
                intent = new Intent(activity, MoreActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
