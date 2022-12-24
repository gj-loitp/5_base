package vn.loitp.a.pattern.mvp

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
class User : BaseModel {
    var fullName = ""
    var email = ""

    constructor()

    constructor(
        fullName: String,
        email: String
    ) {
        this.fullName = fullName
        this.email = email
    }

    override fun toString(): String {
        return "Email : $email\nFullName : $fullName"
    }
}
