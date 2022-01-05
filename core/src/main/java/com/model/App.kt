package com.model

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class App : BaseModel() {

    @SerializedName("pkg")
    @Expose
    var pkg: String? = null

    @SerializedName("config")
    @Expose
    var config: Config? = null
}
