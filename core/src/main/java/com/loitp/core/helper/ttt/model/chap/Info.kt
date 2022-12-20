package com.loitp.core.helper.ttt.model.chap

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
