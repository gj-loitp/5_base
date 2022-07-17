package com.loitpcore.function.epub.model

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class BookInfoData private constructor() : BaseModel() {

    companion object {
        val instance = BookInfoData()
    }

    var bookInfo: BookInfo? = null
}
