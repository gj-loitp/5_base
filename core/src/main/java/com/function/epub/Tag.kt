package com.function.epub

import com.core.base.BaseModel

class Tag : BaseModel() {

    companion object {
        private const val serialVersionUID = -7115489705388170603L
    }

    var tagName: String? = null
    var fullTagName: String? = null
    var openingTagStartPosition = 0
    var closingTagStartPosition = 0
    var isOmitted = false
}
