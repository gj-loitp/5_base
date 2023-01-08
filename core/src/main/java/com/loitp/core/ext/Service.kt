package com.loitp.core.ext

import android.os.Handler
import android.os.Looper

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun setDelay(
    mls: Int,
    runnable: Runnable
) {
    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({ runnable.run() }, mls.toLong())
}

val isUIThread: Boolean
    get() = Looper.myLooper() == Looper.getMainLooper()

