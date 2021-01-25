package com.core.helper.ttt.model.comic

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Keep
class Comics : BaseModel() {
    @SerializedName("comic")
    @Expose
    var comic: List<Comic> = ArrayList()

}
