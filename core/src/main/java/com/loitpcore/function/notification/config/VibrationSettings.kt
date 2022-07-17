package com.loitpcore.function.notification.config

import androidx.annotation.Keep

@Keep
class VibrationSettings {

    companion object {
        var STD_VIBRATION = longArrayOf(300, 200, 300, 200)
        var FAST_VIBRATION = longArrayOf(100, 100, 100, 100, 100, 100)
    }

    var pattern: LongArray? = null
    var isVibrate = true

    constructor(vararg arg: Long) {
        pattern = arg
    }

    constructor(pattern: LongArray, vibrate: Boolean) {
        this.pattern = pattern
        isVibrate = vibrate
    }

    constructor(vibrate: Boolean) {
        isVibrate = vibrate
    }
}
