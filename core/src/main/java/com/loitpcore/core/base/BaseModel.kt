package com.loitpcore.core.base

import java.io.Serializable

open class BaseModel : Serializable {

    fun deepCopy(): BaseModel {
        return BaseApplication.gson.fromJson(BaseApplication.gson.toJson(this), this.javaClass)
    }
}
