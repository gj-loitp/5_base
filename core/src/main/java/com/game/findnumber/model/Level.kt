package com.game.findnumber.model

import androidx.annotation.Keep
import com.core.base.BaseModel

/**
 * Created by ©Loitp93 on 12/1/2020.
 * VinHMS
 * www.muathu@gmail.com
 */
@Keep
class Level : BaseModel() {

    companion object {
        const val STATUS_LEVEL_OPEN = 0
        const val STATUS_LEVEL_WIN = 1
    }

    var name: String? = null
    var status: Int = STATUS_LEVEL_OPEN
}
