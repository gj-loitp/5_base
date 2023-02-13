package vn.loitp.up.a.db.sqliteMultiTableAdvance.md

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
class Action : BaseModel() {
    companion object {
        const val ACTION_CREATE = 0
        const val ACTION_EDIT = 1
        @Suppress("unused")
        const val ACTION_SEND_TO_VENDOR = 2
        @Suppress("unused")
        const val ACTION_DELETE = 3
        @Suppress("unused")
        const val ACTION_CONFIRM = 4
        @Suppress("unused")
        const val ACTION_DENY = 5
    }

    var id: Int = 0
    var actionType: Int? = null
    var inspection: Inspection? = null
}
