package com.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Config {
    @SerializedName("isReady")
    @Expose
    var isIsReady: Boolean = false

    @SerializedName("isFullData")
    @Expose
    var isFullData: Boolean = false

    @SerializedName("bkgSplash")
    @Expose
    var bkgSplash: String? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null
}
