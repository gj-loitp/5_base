package vn.loitp.core.base;

import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by loitp on 6/3/2018.
 */

public abstract class BaseFontActivity extends BaseActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
