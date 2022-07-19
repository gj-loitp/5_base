package com.loitpcore.restApi.flickr.model.photoSetGetList

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Photosets : BaseModel() {
    @SerializedName("page")
    @Expose
    var page: String? = null

    @SerializedName("pages")
    @Expose
    var pages = 0.0

    @SerializedName("perpage")
    @Expose
    var perpage: String? = null

    @SerializedName("total")
    @Expose
    var total = 0.0

    @SerializedName("photoset")
    @Expose
    var photoset = ArrayList<Photoset>()
}