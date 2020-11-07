package com.function.notification.config

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

    constructor(defaultActionImage: Int,
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
