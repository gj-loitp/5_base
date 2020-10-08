package com.restapi.flickr.model.photosetgetlist

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WrapperPhotosetGetlist : BaseModel() {
    @SerializedName("photosets")
    @Expose
    var photosets: Photosets? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null

}
