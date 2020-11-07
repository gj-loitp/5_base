package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class RequestLogin(

        @SerializedName("email")
        @Expose
        val email: String? = null,

        @SerializedName("password")
        @Expose
        val password: String? = null
)
