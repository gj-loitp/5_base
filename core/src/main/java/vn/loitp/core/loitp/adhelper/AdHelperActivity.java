package vn.loitp.core.loitp.adhelper;

import android.os.Bundle;

import loitp.core.R;
import vn.loitp.core.base.BaseActivity;

/**
 * Created by LENOVO on 5/31/2018.
 */

public class AdHelperActivity extends BaseActivity {


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
        return R.layout.activity_ad_helper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;

    }
}
