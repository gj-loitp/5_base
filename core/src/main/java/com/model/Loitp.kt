package com.model

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class Loitp : BaseModel() {

    @SerializedName("loitp")
    @Expose
    var loitp: List<App>? = null
}
