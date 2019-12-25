package vn.loitp.app.activity.api.coroutine.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
data class ErrorJson(
        @SerializedName("errors")
        @Expose
        val errors: List<ErrorResponse>? = null
)