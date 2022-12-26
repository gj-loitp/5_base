package com.loitp.core.helper.ttt.model

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
