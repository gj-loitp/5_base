package vn.loitp.app.activity.api.coroutine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
data class UserTest(
        @SerializedName("id")
        @Expose
        var id: Int?,

        @SerializedName("email")
        @Expose
        var email: String?,

        @SerializedName("first_name")
        @Expose
        var firstName: String?,

        @SerializedName("last_name")
        @Expose
        var lastName: String?,

        @SerializedName("avatar")
        @Expose
        var avatar: String?
) : Serializable