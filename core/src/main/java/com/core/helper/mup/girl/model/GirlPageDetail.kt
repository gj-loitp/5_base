package com.core.helper.mup.girl.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
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
        val createdDate: String?,

        @SerializedName("isFavorites")
        @Expose
        var isFavorites: Boolean = false
) : Serializable
