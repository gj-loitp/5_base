package vn.loitp.app.activity.database.sqlitemultitableadvance.model

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class Inspection : BaseModel() {
    var id: Int = 0
    var inspectionId: String? = null
    var content: String? = null
}
