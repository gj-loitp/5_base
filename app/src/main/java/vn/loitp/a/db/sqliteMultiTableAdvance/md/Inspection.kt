package vn.loitp.a.db.sqliteMultiTableAdvance.md

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@Keep
class Inspection : BaseModel() {
    var id: Int = 0
    var inspectionId: String? = null
    var content: String? = null
}
