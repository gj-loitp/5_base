package com.core.utilities.statusbar

import android.os.Looper

object LThreadUtil {
    val isUIThread: Boolean
        get() = Looper.myLooper() == Looper.getMainLooper()
}
