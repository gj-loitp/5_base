package vn.loitp.app.a.tutorial.rxjava2.md

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
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
