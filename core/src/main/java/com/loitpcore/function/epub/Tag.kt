package com.loitpcore.function.epub

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
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
