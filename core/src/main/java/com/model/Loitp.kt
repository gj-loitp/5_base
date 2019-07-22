package com.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Loitp {

    @SerializedName("loitp")
    @Expose
    var loitp: List<App>? = null

}
