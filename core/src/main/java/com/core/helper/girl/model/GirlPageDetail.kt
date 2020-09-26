package com.core.helper.girl.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GirlPageDetail(
        @SerializedName("src")
        @Expose
        val src: String?,

        @SerializedName("linkId")
        @Expose
        val linkId: String?,

        @SerializedName("id")
        @Expose
        val id: String?,

        @SerializedName("createdDate")
        @Expose
        val createdDate: String?
) : Serializable
