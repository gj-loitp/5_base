package com.loitpcore.core.helper.ttt.model.chap

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Info : BaseModel() {

    @SerializedName("cover")
    @Expose
    var cover: String = ""

    @SerializedName("other_name")
    @Expose
    var otherName: String = ""

    @SerializedName("author")
    @Expose
    var author: String = ""

    @SerializedName("type")
    @Expose
    var type: String = ""

    @SerializedName("new_chap")
    @Expose
    var newChap: String = ""

    @SerializedName("summary")
    @Expose
    var summary: String = ""
}
