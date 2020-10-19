package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Login(
        val createdDate: String? = null,
        val email: String? = null,
        val isVerified: Boolean? = null,
        val jwtToken: String? = null,
        val modifiedDate: String? = null,
        val name: String? = null,
        val role: String? = null
) : Serializable
