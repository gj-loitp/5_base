package com.core.base

import android.content.Context
import io.github.inflationx.viewpump.ViewPumpContextWrapper


/**
 * Created by loitp on 6/3/2018.
 */

abstract class BaseFontActivity : BaseActivity() {

    override fun attachBaseContext(newBase: Context) {
        //super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
