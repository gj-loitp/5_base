package vn.loitp.app.activity.pattern.mvp

import com.core.base.BaseModel

class User : BaseModel {
    var fullName = ""
    var email = ""

    constructor()

    constructor(fullName: String, email: String) {
        this.fullName = fullName
        this.email = email
    }

    override fun toString(): String {
        return "Email : $email\nFullName : $fullName"
    }
}
