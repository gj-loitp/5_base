package com.loitpcore.function.notification.config

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class LightSettings : BaseModel {
    var argb: Int
    var onMs = 300
    var offMs = 3000

    constructor(argb: Int, onMs: Int, offMs: Int) {
        this.argb = argb
        this.onMs = onMs
        this.offMs = offMs
    }

    constructor(argb: Int) {
        this.argb = argb
    }
}
