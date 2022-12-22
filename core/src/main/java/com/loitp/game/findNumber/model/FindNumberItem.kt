package com.loitp.game.findNumber.model

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
@Suppress("unused")
class FindNumberItem : BaseModel() {

    companion object {
        const val STATUS_OPEN = 0
        const val STATUS_CLOSE = 1
    }

    var name: String? = null
    var rotate: Float = 0f
    var status = STATUS_OPEN
}
