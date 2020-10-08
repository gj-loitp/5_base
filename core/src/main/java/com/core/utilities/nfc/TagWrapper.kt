package com.core.utilities.nfc

import com.core.base.BaseModel

class TagWrapper(val id: String) : BaseModel() {
    var techList = TagTechList()

}
