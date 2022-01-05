package com.core.base

import android.content.Context
import io.github.inflationx.viewpump.ViewPumpContextWrapper

abstract class BaseFontActivity : BaseActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
