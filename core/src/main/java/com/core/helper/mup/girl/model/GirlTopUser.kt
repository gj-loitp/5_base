package com.core.helper.mup.girl.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class GirlTopUser(
    @SerializedName("avatar")
    @Expose
    val avatar: String?,

    @SerializedName("name")
    @Expose
    val name: String?
) : Serializable
