package com.loitp.core.ext

import android.annotation.SuppressLint
import android.util.Log
import kotlin.math.min

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
private const val maxLogSize = 4000

@SuppressLint("LogNotTimber")
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
