package vn.loitp.up.a.db.sqlite

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
class Contact : BaseModel {
    var id: Int = 0
    var name: String? = null
    var phoneNumber: String? = null

    constructor()

    constructor(id: Int, name: String, phoneNumber: String) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
    }

    constructor(name: String, phoneNumber: String) {
        this.name = name
        this.phoneNumber = phoneNumber
    }
}
