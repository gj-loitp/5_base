package com.core.base

import androidx.annotation.Keep
import java.io.Serializable

@Keep
open class BaseModel : Serializable {

    fun deepCopy(): BaseModel {
        return BaseApplication.gson.fromJson(BaseApplication.gson.toJson(this), this.javaClass)
    }
}
