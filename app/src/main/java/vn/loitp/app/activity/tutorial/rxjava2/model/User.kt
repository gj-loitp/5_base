package vn.loitp.app.activity.tutorial.rxjava2.model

class User {
    @JvmField
    var id: Long = 0

    @JvmField
    var firstname: String? = null

    @JvmField
    var lastname: String? = null
    var isFollowing = false

    constructor() {}

    constructor(apiUser: ApiUser) {
        id = apiUser.id
        firstname = apiUser.firstname
        lastname = apiUser.lastname
    }

    override fun toString(): String {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isFollowing=" + isFollowing +
                '}'
    }

    override fun hashCode(): Int {
        return id.toInt() + firstname.hashCode() + lastname.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other is User) {
            return id == other.id && firstname == other.firstname && lastname == other.lastname
        }
        return false
    }
}
