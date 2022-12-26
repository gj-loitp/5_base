package com.loitp.restApi.flickr.model.photoSetGetPhotos

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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
    @Suppress("unused")
    var owner: String? = null

    @SerializedName("ownername")
    @Expose
    @Suppress("unused")
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
