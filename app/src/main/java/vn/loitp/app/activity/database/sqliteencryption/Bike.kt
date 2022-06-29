package vn.loitp.app.activity.database.sqliteencryption

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class Bike : BaseModel() {
    var id: Long? = 0
    var name: String? = ""
    var branch: String? = ""
    var hp: Int? = 0
    var price: Int? = 0
    var imgPath0: String? = ""
    var imgPath1: String? = ""
    var imgPath2: String? = ""
}
