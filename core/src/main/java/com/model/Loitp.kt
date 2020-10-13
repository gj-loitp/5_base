package com.model

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Loitp : BaseModel() {

    @SerializedName("loitp")
    @Expose
    var loitp: List<App>? = null

}
