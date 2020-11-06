package com.function.epub.model

import com.core.base.BaseModel

class BookInfoData private constructor() : BaseModel() {

    companion object {
        val instance = BookInfoData()
    }

    var bookInfo: BookInfo? = null

}
