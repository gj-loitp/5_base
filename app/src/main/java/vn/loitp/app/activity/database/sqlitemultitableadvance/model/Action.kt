package vn.loitp.app.activity.database.sqlitemultitableadvance.model

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class Action : BaseModel() {
    companion object {
        const val ACTION_CREATE = 0
        const val ACTION_EDIT = 1
        const val ACTION_SEND_TO_VENDOR = 2
        const val ACTION_DELETE = 3
        const val ACTION_CONFIRM = 4
        const val ACTION_DENY = 5
    }

    var id: Int = 0
    var actionType: Int? = null
    var inspection: Inspection? = null
}
