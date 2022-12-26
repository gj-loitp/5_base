package com.loitp.func.epub

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
