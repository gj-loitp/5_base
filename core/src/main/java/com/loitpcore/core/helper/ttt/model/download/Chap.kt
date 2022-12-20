package com.loitpcore.core.helper.ttt.model.download

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
class Chap : BaseModel() {
    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("size")
    @Expose
    var size = 0
}
