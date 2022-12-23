package vn.loitp.a.db.readSqliteAsset

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
class Vocabulary : BaseModel() {
    var isClose: Boolean = false
    var id: Int = 0
    var sword: String? = null
    var sphonetic: String? = null
    var smeanings: String? = null
    var ssummary: String? = null
    var sisoxfordlist: Int = 0
}
