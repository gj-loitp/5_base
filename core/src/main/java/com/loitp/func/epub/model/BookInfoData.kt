package com.loitp.func.epub.model

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
class BookInfoData private constructor() : BaseModel() {

    companion object {
        val instance = BookInfoData()
    }

    var bookInfo: BookInfo? = null
}
