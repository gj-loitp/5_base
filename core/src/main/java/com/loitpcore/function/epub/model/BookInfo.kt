package com.loitpcore.function.epub.model

import android.graphics.Bitmap
import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class BookInfo : BaseModel() {

    var title: String? = null
    var coverImage: ByteArray? = null
    var filePath: String? = null
    var isCoverImageNotExists = false
    var coverImageBitmap: Bitmap? = null
}
