package com.core.helper.girl.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GirlPage(
        @SerializedName("src")
        @Expose
        val src: String?,

        @SerializedName("title")
        @Expose
        val title: String?,

        @SerializedName("id")
        @Expose
        val id: String?,

        @SerializedName("createdDate")
        @Expose
        val createdDate: String?
)
