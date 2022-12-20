package com.loitpcore.function.epub.model

import android.graphics.Bitmap
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
class BookInfo : BaseModel() {

    var title: String? = null
    var coverImage: ByteArray? = null
    var filePath: String? = null
    var isCoverImageNotExists = false
    var coverImageBitmap: Bitmap? = null
}
