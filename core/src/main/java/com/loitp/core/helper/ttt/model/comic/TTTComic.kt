package com.loitp.core.helper.ttt.model.comic

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
@Suppress("unused")
class TTTComic : BaseModel() {

    @SerializedName("comics")
    @Expose
    var comics: Comics? = null

    @SerializedName("time_upload")
    @Expose
    var timeUpload: String = ""
}
