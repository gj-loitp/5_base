package com.loitp.sv.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
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

) : Serializable
