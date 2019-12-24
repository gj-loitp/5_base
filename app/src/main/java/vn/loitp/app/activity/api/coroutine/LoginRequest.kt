package vn.loitp.app.activity.api.coroutine

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
data class LoginRequest(
        @SerializedName("userId")
        @Expose
        val userId: String? = null,

        @SerializedName("token")
        @Expose
        val token: String? = null,

        @SerializedName("email")
        @Expose
        val email: String? = null,

        @SerializedName("deviceId")
        @Expose
        val deviceId: String? = null,

        @SerializedName("platform")
        @Expose
        val platform: String? = "Android",

        @SerializedName("firebaseRegistrationKey")
        @Expose
        val firebaseRegistrationKey: String? = null
)