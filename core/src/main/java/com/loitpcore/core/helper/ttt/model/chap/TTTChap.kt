package com.loitpcore.core.helper.ttt.model.chap

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class TTTChap : BaseModel() {
    @SerializedName("chaps")
    @Expose
    var chaps: Chaps? = null

    @SerializedName("info")
    @Expose
    var info: Info? = null
}
