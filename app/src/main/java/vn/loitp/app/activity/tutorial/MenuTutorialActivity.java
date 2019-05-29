package vn.loitp.app.activity.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.tutorial.retrofit2.Retrofit2Activity;
import vn.loitp.app.activity.tutorial.rxjava2.MenuRxJava2Activity;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;

public class MenuTutorialActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_rx_java_2).setOnClickListener(this);
        findViewById(R.id.bt_retrofit_2).setOnClickListener(this);
        LLog.d(TAG, "1");
        LLog.d(TAG, "2");
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
        return R.layout.activity_tutorial_menu;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_rx_java_2:
                intent = new Intent(activity, MenuRxJava2Activity.class);
                break;
            case R.id.bt_retrofit_2:
                intent = new Intent(activity, Retrofit2Activity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
