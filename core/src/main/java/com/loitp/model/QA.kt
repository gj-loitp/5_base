package com.loitp.model

import androidx.annotation.Keep

@Keep
data class QA(
    //answer
    val a: String = "",
    val ivA: String = "",
    //question
    val q: String = "",
    val ivQ: String = "",
    //next link if clicked
    val nextLink: String = "",
)
