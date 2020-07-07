package vn.loitp.app.activity.api.retrofit2

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by LENOVO on 2/23/2018.
 */
class SOAnswersResponse {
    @SerializedName("items")
    @Expose
    var items: List<Item>? = null

    @SerializedName("has_more")
    @Expose
    var hasMore: Boolean? = null

    @SerializedName("backoff")
    @Expose
    var backoff: Int? = null

    @SerializedName("quota_max")
    @Expose
    var quotaMax: Int? = null

    @SerializedName("quota_remaining")
    @Expose
    var quotaRemaining: Int? = null

}
