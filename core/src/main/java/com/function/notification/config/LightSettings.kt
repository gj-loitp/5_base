package com.function.notification.config

import com.core.base.BaseModel

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
