package com.loitpcore.restapi.flickr.model.photosetgetlist

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class WrapperPhotosetGetlist : BaseModel() {
    @SerializedName("photosets")
    @Expose
    var photosets: Photosets? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null
}
