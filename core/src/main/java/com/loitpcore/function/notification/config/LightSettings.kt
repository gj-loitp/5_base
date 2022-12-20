package com.loitpcore.function.notification.config

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class LightSettings : BaseModel {
    var argb: Int
    var onMs = 300
    var offMs = 3000

    @Suppress("unused")
    constructor(
        argb: Int,
        onMs: Int,
        offMs: Int
    ) {
        this.argb = argb
        this.onMs = onMs
        this.offMs = offMs
    }

    constructor(argb: Int) {
        this.argb = argb
    }
}
