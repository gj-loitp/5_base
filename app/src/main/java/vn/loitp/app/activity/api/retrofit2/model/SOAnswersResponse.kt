package vn.loitp.app.activity.api.retrofit2.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class SOAnswersResponse : BaseModel() {
    @SerializedName("items")
    @Expose
    var items: List<Item>? = null

    @SerializedName("has_more")
    @Expose
    @Suppress("unused")
    var hasMore: Boolean? = null

    @SerializedName("backoff")
    @Expose
    @Suppress("unused")
    var backoff: Int? = null

    @SerializedName("quota_max")
    @Expose
    @Suppress("unused")
    var quotaMax: Int? = null

    @SerializedName("quota_remaining")
    @Expose
    @Suppress("unused")
    var quotaRemaining: Int? = null
}
