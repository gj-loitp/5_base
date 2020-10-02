package com.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class App {

    @SerializedName("pkg")
    @Expose
    var pkg: String? = null

    @SerializedName("config")
    @Expose
    var config: Config? = null

}
