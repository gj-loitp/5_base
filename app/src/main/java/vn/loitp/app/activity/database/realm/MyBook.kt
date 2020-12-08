package vn.loitp.app.activity.database.realm


import androidx.annotation.Keep
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by www.muathu@gmail.com on 1/18/2018.
 */

@Keep
open class MyBook : RealmObject() {
    @PrimaryKey
    open var id: Long = 0
    open var title: String? = null
    open var author: String? = null
}
