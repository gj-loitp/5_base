package com.loitp.core.utilities

import android.os.Looper

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LThreadUtil {
    companion object {
        val isUIThread: Boolean
            get() = Looper.myLooper() == Looper.getMainLooper()
    }
}
