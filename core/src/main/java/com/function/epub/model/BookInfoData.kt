package com.function.epub.model

import com.core.base.BaseModel

class BookInfoData private constructor() : BaseModel() {

    var bookInfo: BookInfo? = null

    companion object {
        val instance = BookInfoData()
    }
}
