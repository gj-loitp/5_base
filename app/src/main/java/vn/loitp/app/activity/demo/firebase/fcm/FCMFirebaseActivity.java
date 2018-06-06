package vn.loitp.app.activity.demo.firebase.fcm;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.app.activity.BaseFontActivity;

//https://docs.google.com/document/d/1LIkZgTWQB7FWVHUrc-ZrQaMVJkm6lbuMwirr1XCPwcA/edit
public class FCMFirebaseActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return R.layout.activity_fcm_firebase;
    }
}
