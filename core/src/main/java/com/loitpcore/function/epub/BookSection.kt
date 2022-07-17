package com.loitpcore.function.epub

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class BookSection : BaseModel() {

    var label: String? = null
    var extension: String? = null
    var sectionContent: String? = null
    var mediaType: String? = null
    var sectionTextContent: String? = null
}
