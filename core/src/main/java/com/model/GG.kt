package com.model

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GG : BaseModel() {

    @SerializedName("pkg")
    @Expose
    var pkg: String = ""

    @SerializedName("isReady")
    @Expose
    var isReady: Boolean = false

}
