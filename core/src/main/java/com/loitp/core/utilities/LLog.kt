package com.loitp.core.utilities

import android.annotation.SuppressLint
import android.util.Log
import kotlin.math.min

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object LLog {

    private const val maxLogSize = 4000

    @SuppressLint("LogNotTimber")
    @JvmStatic
    fun d(
        tag: String,
        msg: String
    ) {
        var i = 0
        while (i < msg.length) {
            Log.d(tag, msg.substring(i, min(a = msg.length, b = i + maxLogSize)))
            i += maxLogSize
        }
    }

    @SuppressLint("LogNotTimber")
    @JvmStatic
    fun e(
        tag: String,
        msg: String
    ) {
        var i = 0
        while (i < msg.length) {
            Log.e(tag, msg.substring(i, min(a = msg.length, b = i + maxLogSize)))
            i += maxLogSize
        }
    }

    @SuppressLint("LogNotTimber")
    @JvmStatic
    fun i(
        tag: String,
        msg: String
    ) {
        var i = 0
        while (i < msg.length) {
            Log.i(tag, msg.substring(i, min(a = msg.length, b = i + maxLogSize)))
            i += maxLogSize
        }
    }
}
