package vn.loitp.app.activity.api.retrofit2

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Owner {
    @SerializedName("reputation")
    @Expose
    var reputation: Int? = null

    @SerializedName("user_id")
    @Expose
    var userId: Int? = null

    @SerializedName("user_type")
    @Expose
    var userType: String? = null

    @SerializedName("profile_image")
    @Expose
    var profileImage: String? = null

    @SerializedName("display_name")
    @Expose
    var displayName: String? = null

    @SerializedName("link")
    @Expose
    var link: String? = null

    @SerializedName("accept_rate")
    @Expose
    var acceptRate: Int? = null

}
