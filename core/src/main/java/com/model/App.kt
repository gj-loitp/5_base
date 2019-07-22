package com.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class App {

    @SerializedName("pkg")
    @Expose
    var pkg: String? = null
    @SerializedName("config")
    @Expose
    var config: Config? = null

}
