package com.loitpcore.model

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
class Config : BaseModel() {
    @SerializedName("isReady")
    @Expose
    var isReady: Boolean? = false

    @SerializedName("isFullData")
    @Expose
    var isFullData: Boolean? = false

    @SerializedName("msg")
    @Expose
    var msg: String? = null
}
