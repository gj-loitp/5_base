package vn.loitp.app.activity.database.sqlite

import androidx.annotation.Keep
import com.core.base.BaseModel

/**
 * Created by www.muathu@gmail.com on 1/19/2018.
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