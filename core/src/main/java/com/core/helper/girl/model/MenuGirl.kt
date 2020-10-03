package com.core.helper.girl.model

import androidx.annotation.Keep

@Keep
data class MenuGirl(
        val itemId: Int,
        val iconId: Int,
        val textId: Int,
        val activeColor: Int
)
