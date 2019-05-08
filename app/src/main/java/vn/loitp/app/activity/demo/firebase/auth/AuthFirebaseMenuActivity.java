package vn.loitp.app.activity.demo.firebase.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

//https://github.com/firebase/quickstart-android
public class AuthFirebaseMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_gg).setOnClickListener(this);
        findViewById(R.id.bt_fb).setOnClickListener(this);
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
        return R.layout.activity_auth_firebase;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_gg:
                intent = new Intent(activity, AuthFirebaseGoogleActivity.class);
                break;
            case R.id.bt_fb:
                intent = new Intent(activity, AuthFirebaseFacebookActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.INSTANCE.tranIn(activity);
        }
    }
}
