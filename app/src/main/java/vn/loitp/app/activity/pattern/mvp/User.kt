package vn.loitp.app.activity.pattern.mvp

class User {
    var fullName = ""
    var email = ""

    constructor() {}

    constructor(fullName: String, email: String) {
        this.fullName = fullName
        this.email = email
    }

    override fun toString(): String {
        return "Email : $email\nFullName : $fullName"
    }
}