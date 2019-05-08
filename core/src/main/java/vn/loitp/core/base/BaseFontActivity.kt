package vn.loitp.core.base

import android.content.Context

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by loitp on 6/3/2018.
 */

abstract class BaseFontActivity : BaseActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
