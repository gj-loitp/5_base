package com.function.epub.model

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class BookInfoData private constructor() : BaseModel() {

    companion object {
        val instance = BookInfoData()
    }

    var bookInfo: BookInfo? = null
}
