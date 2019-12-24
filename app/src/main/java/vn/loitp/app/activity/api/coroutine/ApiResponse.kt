package vn.loitp.app.activity.api.coroutine

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
data class ApiResponse<T>(

        @SerializedName("errorCode")
        @Expose
        val errorCode: Int? = null,

        @SerializedName("status")
        @Expose
        val status: Boolean? = null,

        @SerializedName("page")
        @Expose
        val page: Int? = null,

        @SerializedName("data")
        @Expose
        val data: T? = null,

        @SerializedName("offset")
        @Expose
        val offset: Int? = null,

        @SerializedName("limit")
        @Expose
        val limit: Int? = null,

        @SerializedName("total")
        @Expose
        val total: Int? = null,

        @SerializedName("total_page")
        @Expose
        val totalPage: Int? = null,

        @SerializedName("errors")
        @Expose
        val errors: ErrorResponse? = null

)
