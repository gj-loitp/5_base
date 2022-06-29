package com.loitpcore.model

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class GG : BaseModel() {

    @SerializedName("pkg")
    @Expose
    var pkg: String = ""

    @SerializedName("isReady")
    @Expose
    var isReady: Boolean = false
}
