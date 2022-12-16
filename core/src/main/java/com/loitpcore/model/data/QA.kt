package com.loitpcore.model.data

import androidx.annotation.Keep

@Keep
data class QA(
    val a: String = "",
    val q: String = "",
    val nextLink: String = "",
)
