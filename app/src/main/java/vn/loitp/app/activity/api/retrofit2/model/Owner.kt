package vn.loitp.app.activity.api.retrofit2.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class Owner : BaseModel() {
    @SerializedName("reputation")
    @Expose
    @Suppress("unused")
    var reputation: Int? = null

    @SerializedName("user_id")
    @Expose
    @Suppress("unused")
    var userId: Int? = null

    @SerializedName("user_type")
    @Expose
    @Suppress("unused")
    var userType: String? = null

    @SerializedName("profile_image")
    @Expose
    @Suppress("unused")
    var profileImage: String? = null

    @SerializedName("display_name")
    @Expose
    var displayName: String? = null

    @SerializedName("link")
    @Expose
    @Suppress("unused")
    var link: String? = null

    @SerializedName("accept_rate")
    @Expose
    @Suppress("unused")
    var acceptRate: Int? = null
}
