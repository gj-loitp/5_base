package com.core.helper.mup.girl.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class MenuGirl(
    val itemId: Int,
    val iconId: Int,
    val textId: Int,
    val activeColor: Int
) : Serializable
