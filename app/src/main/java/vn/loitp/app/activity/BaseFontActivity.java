package vn.loitp.app.activity;

import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import vn.loitp.core.base.BaseActivity;

/**
 * Created by LENOVO on 6/3/2018.
 */

public class BaseFontActivity extends BaseActivity {
    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return null;
    }

    @Override
    protected int setLayoutResourceId() {
        return 0;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
