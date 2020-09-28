package com.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class GG {

    @SerializedName("pkg")
    @Expose
    var pkg: String = ""

    @SerializedName("isReady")
    @Expose
    var isReady: Boolean = false

}
