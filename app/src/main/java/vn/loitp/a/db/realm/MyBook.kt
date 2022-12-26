package vn.loitp.a.db.realm

import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

@Keep
open class MyBook : RealmObject() {
    @PrimaryKey
    open var id: Long = 0
    open var title: String? = null
    open var author: String? = null
}
