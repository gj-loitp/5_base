package vn.loitp.app.activity.tutorial.rxjava2.model

import com.core.base.BaseModel

class ApiUser : BaseModel() {
    @JvmField
    var id: Long = 0

    @JvmField
    var firstname: String? = null

    @JvmField
    var lastname: String? = null

    override fun toString(): String {
        return "ApiUser{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}'
    }
}
