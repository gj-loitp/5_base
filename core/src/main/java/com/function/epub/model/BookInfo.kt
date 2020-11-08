package com.function.epub.model

import android.graphics.Bitmap
import androidx.annotation.Keep
import com.core.base.BaseModel

/**
 * Created by loitp on 08.09.2016.
 */

@Keep
class BookInfo : BaseModel() {

    var title: String? = null
    var coverImage: ByteArray? = null
    var filePath: String? = null
    var isCoverImageNotExists = false
    var coverImageBitmap: Bitmap? = null

}
