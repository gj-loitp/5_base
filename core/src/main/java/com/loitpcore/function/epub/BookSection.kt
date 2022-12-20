package com.loitpcore.function.epub

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class BookSection : BaseModel() {

    var label: String? = null
    var extension: String? = null
    var sectionContent: String? = null
    var mediaType: String? = null
    var sectionTextContent: String? = null
}
