package com.core.utilities

import android.util.Log
import kotlin.math.min

object LLog {

    private const val maxLogSize = 4000

    @JvmStatic
    fun d(tag: String, msg: String) {
        var i = 0
        while (i < msg.length) {
            Log.d(tag, msg.substring(i, min(a = msg.length, b = i + maxLogSize)))
            i += maxLogSize
        }
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        var i = 0
        while (i < msg.length) {
            Log.e(tag, msg.substring(i, min(a = msg.length, b = i + maxLogSize)))
            i += maxLogSize
        }
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        var i = 0
        while (i < msg.length) {
            Log.i(tag, msg.substring(i, min(a = msg.length, b = i + maxLogSize)))
            i += maxLogSize
        }
    }
}
