package com.loitp.restApi.flickr.model.photoSetGetPhotos

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel
import com.loitp.core.ext.getFlickrLink100
import com.loitp.core.ext.getFlickrLink1024
import com.loitp.core.ext.getFlickrLink320
import com.loitp.core.ext.getFlickrLink640

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class Photo : BaseModel() {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("secret")
    @Expose
    @Suppress("unused")
    var secret: String? = null

    @SerializedName("server")
    @Expose
    @Suppress("unused")
    var server: String? = null

    @SerializedName("farm")
    @Expose
    @Suppress("unused")
    var farm = 0.0

    @SerializedName("title")
    @Expose
    var title: String = ""

    @SerializedName("isprimary")
    @Expose
    @Suppress("unused")
    var isprimary: String? = null

    @SerializedName("ispublic")
    @Expose
    @Suppress("unused")
    var ispublic = 0.0

    @SerializedName("isfriend")
    @Expose
    @Suppress("unused")
    var isfriend = 0.0

    @SerializedName("isfamily")
    @Expose
    @Suppress("unused")
    var isfamily = 0.0

    @SerializedName("url_o")
    @Expose
    var urlO: String = ""

    @SerializedName("height_o")
    @Expose
    var heightO = 1

    @SerializedName("width_o")
    @Expose
    var widthO = 1

    @SerializedName("url_s")
    @Expose
    var urlS: String = ""

    @SerializedName("height_s")
    @Expose
    @Suppress("unused")
    var heightS = 1

    @SerializedName("width_s")
    @Expose
    @Suppress("unused")
    var widthS = 1

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

    @Suppress("unused")
    // gif extension have no link large
    val flickrLink100: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            getFlickrLink100(urlM)
        }

    @Suppress("unused")
    // gif extension have no link large
    val flickrLink640: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            getFlickrLink640(urlM)
        }

    // gif extension have no link large
    val flickrLink1024: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            getFlickrLink1024(urlM)
        }

    @Suppress("unused")
    // gif extension have no link large
    val flickrLink320: String
        get() = if (urlO.contains(".gif")) {
            // gif extension have no link large
            urlO
        } else {
            getFlickrLink320(urlM)
        }

    fun calculatorHeight(widthScreen: Int): Int {
        return heightO * widthScreen / widthO
    }

}
