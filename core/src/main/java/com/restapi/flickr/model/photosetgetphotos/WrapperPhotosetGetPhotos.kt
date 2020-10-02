package com.restapi.flickr.model.photosetgetphotos

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class WrapperPhotosetGetPhotos {
    @SerializedName("photoset")
    @Expose
    var photoset: Photoset? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null

}