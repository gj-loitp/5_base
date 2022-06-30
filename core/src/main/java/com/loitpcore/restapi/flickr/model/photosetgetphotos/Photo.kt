package com.loitpcore.restapi.flickr.model.photosetgetphotos

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel
import com.loitpcore.core.utilities.LImageUtil

@Keep
class Photo : BaseModel() {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("secret")
    @Expose
    var secret: String? = null

    @SerializedName("server")
    @Expose
    var server: String? = null

    @SerializedName("farm")
    @Expose
    var farm = 0.0

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("isprimary")
    @Expose
    var isprimary: String? = null

    @SerializedName("ispublic")
    @Expose
    var ispublic = 0.0

    @SerializedName("isfriend")
    @Expose
    var isfriend = 0.0

    @SerializedName("isfamily")
    @Expose
    var isfamily = 0.0

    @SerializedName("url_o")
    @Expose
    var urlO: String = ""

    @SerializedName("height_o")
    @Expose
    var heightO = 0

    @SerializedName("width_o")
    @Expose
    var widthO = 0

    @SerializedName("url_s")
    @Expose
    var urlS: String = ""

    @SerializedName("height_s")
    @Expose
    var heightS = 0

    @SerializedName("width_s")
    @Expose
    var widthS = 0

    @SerializedName("url_m")
    @Expose
    var urlM: String = ""

    @SerializedName("height_m")
    @Expose
    var heightM = 0

    @SerializedName("width_m")
    @Expose
    var widthM = 0

    // gif extension have no link large
    val flickrLink100: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            LImageUtil.getFlickrLink100(urlM)
        }

    // gif extension have no link large
    val flickrLink640: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            LImageUtil.getFlickrLink640(urlM)
        }

    // gif extension have no link large
    val flickrLink1024: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            LImageUtil.getFlickrLink1024(urlM)
        }

    // gif extension have no link large
    val flickrLink320: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            LImageUtil.getFlickrLink320(urlM)
        }
}
