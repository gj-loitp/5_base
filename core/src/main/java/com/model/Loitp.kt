package com.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class Loitp {

    @SerializedName("loitp")
    @Expose
    var loitp: List<App>? = null

}
