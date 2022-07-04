package com.loitpcore.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Loitp : BaseModel() {

    @SerializedName("loitp")
    @Expose
    var loitp: List<App>? = null
}
