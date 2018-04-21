package vn.loitp.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.ads.MenuAdsActivity;
import vn.loitp.app.activity.animation.MenuAnimationActivity;
import vn.loitp.app.activity.api.MenuAPIActivity;
import vn.loitp.app.activity.customviews.MenuCustomViewsActivity;
import vn.loitp.app.activity.database.MenuDatabaseActivity;
import vn.loitp.app.activity.demo.MenuDemoActivity;
import vn.loitp.app.activity.function.MenuFunctionActivity;
import vn.loitp.app.activity.pattern.MenuPatternActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;

public class MenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;

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
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
