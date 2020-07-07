package com.core.utilities

import android.os.Looper

class LThreadUtil {
    companion object {
        val isUIThread: Boolean
            get() = Looper.myLooper() == Looper.getMainLooper()
    }
}
