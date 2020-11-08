package com.core.helper.adhelper

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class AdPage : BaseModel() {
    var title: String? = null
    var msg: String? = null
    var urlAd: String? = null
}
