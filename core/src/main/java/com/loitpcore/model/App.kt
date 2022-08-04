package com.loitpcore.model

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
class App : BaseModel() {

    @SerializedName("pkg")
    @Expose
    var pkg: String? = null

    @SerializedName("config")
    @Expose
    var config: Config? = null
}
