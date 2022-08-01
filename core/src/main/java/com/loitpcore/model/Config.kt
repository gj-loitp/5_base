package com.loitpcore.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Config : BaseModel() {
    @SerializedName("isReady")
    @Expose
    var isReady: Boolean = false

    @SerializedName("isFullData")
    @Expose
    var isFullData: Boolean = false

    @SerializedName("msg")
    @Expose
    var msg: String? = null
}
