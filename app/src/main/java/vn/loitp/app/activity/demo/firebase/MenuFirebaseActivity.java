package vn.loitp.app.activity.demo.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.BaseFontActivity;
import vn.loitp.app.activity.demo.firebase.admob.FirebaseAdmobActivity;
import vn.loitp.app.activity.demo.firebase.auth.AuthFirebaseMenuActivity;
import vn.loitp.app.activity.demo.firebase.config.ConfigFirebaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

//https://github.com/firebase/quickstart-android
public class MenuFirebaseActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_admob).setOnClickListener(this);
        findViewById(R.id.bt_auth).setOnClickListener(this);
        findViewById(R.id.bt_config).setOnClickListener(this);
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
        return R.layout.activity_menu_firebase;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_admob:
                intent = new Intent(activity, FirebaseAdmobActivity.class);
                break;
            case R.id.bt_auth:
                intent = new Intent(activity, AuthFirebaseMenuActivity.class);
                break;
            case R.id.bt_config:
                intent = new Intent(activity, ConfigFirebaseActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
