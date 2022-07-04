package com.loitpcore.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class App : BaseModel() {

    @SerializedName("pkg")
    @Expose
    var pkg: String? = null

    @SerializedName("config")
    @Expose
    var config: Config? = null
}
