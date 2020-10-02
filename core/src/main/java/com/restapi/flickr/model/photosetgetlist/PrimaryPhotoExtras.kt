package com.restapi.flickr.model.photosetgetlist

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class PrimaryPhotoExtras {
    @SerializedName("url_o")
    @Expose
    var urlO: String = ""

    @SerializedName("height_o")
    @Expose
    var heightO = 0

    @SerializedName("width_o")
    @Expose
    var widthO = 0

    @SerializedName("url_m")
    @Expose
    var urlM: String = ""

    @SerializedName("height_m")
    @Expose
    var heightM = 0

    @SerializedName("width_m")
    @Expose
    var widthM = 0

}
