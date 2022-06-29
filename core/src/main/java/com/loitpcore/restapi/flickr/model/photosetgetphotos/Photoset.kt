package com.loitpcore.restapi.flickr.model.photosetgetphotos

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class Photoset : BaseModel() {
    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("primary")
    @Expose
    var primary: String? = null

    @SerializedName("owner")
    @Expose
    var owner: String? = null

    @SerializedName("ownername")
    @Expose
    var ownername: String? = null

    @SerializedName("photo")
    @Expose
    var photo = ArrayList<Photo>()

    @SerializedName("page")
    @Expose
    var page = 0

    @SerializedName("per_page")
    @Expose
    var perPage = 0

    @SerializedName("perpage")
    @Expose
    var perpage = 0

    @SerializedName("pages")
    @Expose
    var pages = 0

    @SerializedName("total")
    @Expose
    var total = 0

    @SerializedName("title")
    @Expose
    var title: String = ""
}
