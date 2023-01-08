package com.loitp.core.base

import android.content.Context
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
abstract class BaseActivityFont : BaseActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}
