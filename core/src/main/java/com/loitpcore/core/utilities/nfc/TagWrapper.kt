package com.loitpcore.core.utilities.nfc

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class TagWrapper(val id: String) : BaseModel() {
    var techList = TagTechList()
}
