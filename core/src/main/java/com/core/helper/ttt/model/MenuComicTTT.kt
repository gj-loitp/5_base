package com.core.helper.ttt.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class MenuComicTTT(
    @SerializedName("itemId")
    @Expose
    val itemId: Int,

    @SerializedName("iconId")
    @Expose
    val iconId: Int,

    @SerializedName("textId")
    @Expose
    val textId: Int,

    @SerializedName("activeColor")
    @Expose
    val activeColor: Int
) : Serializable
