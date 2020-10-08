package com.restapi.flickr.model.photosetgetphotos

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WrapperPhotosetGetPhotos : BaseModel() {
    @SerializedName("photoset")
    @Expose
    var photoset: Photoset? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null

}
