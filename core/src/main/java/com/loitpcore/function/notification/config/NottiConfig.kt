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
class NottiConfig {
    var defaultActionImage: Int
    var isSameID = false
    var lightSettings: LightSettings? = null
    var vibrationSettings: VibrationSettings? = null

    constructor(
        defaultActionImage: Int
    ) {
        this.defaultActionImage = defaultActionImage
    }

    constructor(
        defaultActionImage: Int,
        vibrationSettings: VibrationSettings?
    ) {
        this.defaultActionImage = defaultActionImage
        this.vibrationSettings = vibrationSettings
    }

    constructor(
        defaultActionImage: Int,
        vibrationSettings: VibrationSettings?,
        lightSettings: LightSettings?
    ) {
        this.defaultActionImage = defaultActionImage
        this.vibrationSettings = vibrationSettings
        this.lightSettings = lightSettings
    }

    fun setSameID(sameID: Boolean): NottiConfig {
        isSameID = sameID
        return this
    }
}
