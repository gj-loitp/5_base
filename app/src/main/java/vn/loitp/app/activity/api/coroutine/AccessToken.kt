package vn.loitp.app.activity.api.coroutine

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
data class AccessToken(
        @SerializedName("accessToken")
        @Expose
        val accessToken: String? = null,

        @SerializedName("refreshToken")
        @Expose
        var refreshToken: String? = null
)