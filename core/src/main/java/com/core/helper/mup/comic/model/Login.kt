package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Login(
        @SerializedName("createdDate")
        @Expose
        val createdDate: String? = null,

        @SerializedName("email")
        @Expose
        val email: String? = null,

        @SerializedName("isVerified")
        @Expose
        val isVerified: Boolean? = null,

        @SerializedName("jwtToken")
        @Expose
        val jwtToken: String? = null,

        @SerializedName("modifiedDate")
        @Expose
        val modifiedDate: String? = null,

        @SerializedName("name")
        @Expose
        val name: String? = null,

        @SerializedName("role")
        @Expose
        val role: String? = null
) : Serializable
