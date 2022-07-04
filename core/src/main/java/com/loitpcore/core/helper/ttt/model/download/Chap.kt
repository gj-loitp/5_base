package com.loitpcore.core.helper.ttt.model.download

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Chap : BaseModel() {
    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("size")
    @Expose
    var size = 0
}
