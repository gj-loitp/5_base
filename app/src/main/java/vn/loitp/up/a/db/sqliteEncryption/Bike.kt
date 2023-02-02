package vn.loitp.up.a.db.sqliteEncryption

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
