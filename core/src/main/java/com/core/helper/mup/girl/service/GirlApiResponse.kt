package com.core.helper.mup.girl.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.service.model.ErrorResponse

data class GirlApiResponse<T>(

    @SerializedName("total")
    @Expose
    val total: Int? = null,

    @SerializedName("items")
    @Expose
    val items: T? = null,

    @SerializedName("limit")
    @Expose
    val limit: Int? = null,

    @SerializedName("page")
    @Expose
    val page: Int? = null,

    @SerializedName("totalPages")
    @Expose
    val totalPages: Int? = null,

    @SerializedName("status")
    @Expose
    val status: Boolean? = null,

    @SerializedName("errorCode")
    @Expose
    val errorCode: Int? = null,

    @SerializedName("errors")
    @Expose
    val errors: ErrorResponse? = null
)
