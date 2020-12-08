package com.core.utilities.nfc

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class TagWrapper(val id: String) : BaseModel() {
    var techList = TagTechList()

}
