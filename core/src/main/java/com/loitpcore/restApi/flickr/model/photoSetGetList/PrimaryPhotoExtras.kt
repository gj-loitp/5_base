package com.loitpcore.restApi.flickr.model.photoSetGetList

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
class PrimaryPhotoExtras : BaseModel() {
    @SerializedName("url_o")
    @Expose
    var urlO: String = ""

    @SerializedName("height_o")
    @Expose
    @Suppress("unused")
    var heightO = 0

    @SerializedName("width_o")
    @Expose
    @Suppress("unused")
    var widthO = 0

    @SerializedName("url_m")
    @Expose
    var urlM: String = ""

    @SerializedName("height_m")
    @Expose
    @Suppress("unused")
    var heightM = 0

    @SerializedName("width_m")
    @Expose
    @Suppress("unused")
    var widthM = 0
}
