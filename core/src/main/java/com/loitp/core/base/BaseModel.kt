package com.loitp.core.base

import java.io.Serializable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class BaseModel : Serializable {

    @Suppress("unused")
    fun deepCopy(): BaseModel {
        return BaseApplication.gson.fromJson(BaseApplication.gson.toJson(this), this.javaClass)
    }
}
