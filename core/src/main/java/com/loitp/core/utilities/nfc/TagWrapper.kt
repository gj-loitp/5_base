package com.loitp.core.utilities.nfc

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
class TagWrapper(val id: String) : BaseModel() {
    var techList = TagTechList()
}
