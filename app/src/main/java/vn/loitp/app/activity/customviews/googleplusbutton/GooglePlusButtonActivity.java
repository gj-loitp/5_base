package vn.loitp.app.activity.customviews.googleplusbutton;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.plus.PlusOneButton;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class GooglePlusButtonActivity extends BaseActivity {
    private PlusOneButton pButtonStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pButtonStandard = (PlusOneButton) findViewById(R.id.plus_one_button_standard);

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
        return R.layout.activity_google_plus_button;
    }

    @Override
    public void onResume() {
        super.onResume();
        //String APP_URL = "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
        String APP_URL = "https://play.google.com/store/apps/details?id=" + "com.ngontinhkangkang.offline30ngontinhoffline";
        pButtonStandard.initialize(APP_URL, 0);
    }
}
