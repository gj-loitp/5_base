package vn.loitp.app.activity.database.realm


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by www.muathu@gmail.com on 1/18/2018.
 */

open class MyBook : RealmObject() {
    @PrimaryKey
    open var id: Long = 0
    open var title: String? = null
    open var author: String? = null
}
