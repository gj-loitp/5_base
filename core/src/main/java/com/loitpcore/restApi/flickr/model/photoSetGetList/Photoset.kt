package com.loitpcore.restApi.flickr.model.photoSetGetList

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel
import com.loitp.core.utilities.LImageUtil

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

    @SerializedName("photos")
    @Expose
    var photos: String? = null

    @SerializedName("videos")
    @Expose
    @Suppress("unused")
    var videos = 0.0

    @SerializedName("title")
    @Expose
    var title: Title? = null

    @SerializedName("description")
    @Expose
    var description: Description? = null

    @SerializedName("needs_interstitial")
    @Expose
    @Suppress("unused")
    var needsInterstitial = 0.0

    @SerializedName("visibility_can_see_set")
    @Expose
    @Suppress("unused")
    var visibilityCanSeeSet = 0.0

    @SerializedName("count_views")
    @Expose
    @Suppress("unused")
    var countViews: String? = null

    @SerializedName("count_comments")
    @Expose
    @Suppress("unused")
    var countComments: String? = null

    @SerializedName("can_comment")
    @Expose
    @Suppress("unused")
    var canComment = 0.0

    @SerializedName("date_create")
    @Expose
    @Suppress("unused")
    var dateCreate: Long = 0

    @SerializedName("date_update")
    @Expose
    var dateUpdate: Long = 0

    @SerializedName("primary_photo_extras")
    @Expose
    var primaryPhotoExtras: PrimaryPhotoExtras? = null

    @Suppress("unused")
    // gif extension have no link large
    fun flickrLinkM(): String {
        return if (primaryPhotoExtras?.urlO?.contains(".gif") == true) {
            // gif extension have no link large
            primaryPhotoExtras?.urlO ?: ""
        } else {
            LImageUtil.getFlickrLink640(primaryPhotoExtras?.urlM ?: "")
        }
    }

    // gif extension have no link large
    fun flickrLinkO(): String {
        return if (primaryPhotoExtras?.urlO?.contains(".gif") == true) {
            // gif extension have no link large
            primaryPhotoExtras?.urlO ?: ""
        } else {
            LImageUtil.getFlickrLink1024(primaryPhotoExtras?.urlM ?: "")
        }
    }

//    val colorBackground = LStoreUtil.randomColorLight
}
