package com.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GG {

    @SerializedName("pkg")
    @Expose
    var pkg: String = ""

    @SerializedName("isReady")
    @Expose
    var isReady: Boolean = false

}
