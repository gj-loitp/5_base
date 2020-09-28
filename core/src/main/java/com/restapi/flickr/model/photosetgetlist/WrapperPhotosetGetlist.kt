package com.restapi.flickr.model.photosetgetlist

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class WrapperPhotosetGetlist {
    @SerializedName("photosets")
    @Expose
    var photosets: Photosets? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null

}
