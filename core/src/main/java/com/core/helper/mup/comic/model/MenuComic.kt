package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class MenuComic(
        val itemId: Int,
        val iconId: Int,
        val textId: Int,
        val activeColor: Int
) : Serializable
