package com.loitpcore.function.notification.config

import androidx.annotation.Keep

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class VibrationSettings {

    companion object {
        var STD_VIBRATION = longArrayOf(300, 200, 300, 200)
        @Suppress("unused")
        var FAST_VIBRATION = longArrayOf(100, 100, 100, 100, 100, 100)
    }

    var pattern: LongArray? = null
    var isVibrate = true

    constructor(vararg arg: Long) {
        pattern = arg
    }

    @Suppress("unused")
    constructor(pattern: LongArray, vibrate: Boolean) {
        this.pattern = pattern
        isVibrate = vibrate
    }

    @Suppress("unused")
    constructor(vibrate: Boolean) {
        isVibrate = vibrate
    }
}
