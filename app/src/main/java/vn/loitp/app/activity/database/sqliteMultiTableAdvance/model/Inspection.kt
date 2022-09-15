package vn.loitp.app.activity.database.sqliteMultiTableAdvance.model

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

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
