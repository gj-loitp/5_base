package com.loitpcore.core.helper.ttt.model.comic

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Comics : BaseModel() {
    @SerializedName("comic")
    @Expose
    var comic: List<Comic> = ArrayList()
}
