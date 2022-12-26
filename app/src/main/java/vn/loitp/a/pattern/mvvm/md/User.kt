package vn.loitp.a.pattern.mvvm.md

import androidx.annotation.Keep
import java.util.* // ktlint-disable no-wildcard-imports

@Keep
class User : Observable() {
    // / The first name of the user
    var firstName: String = ""
        set(value) {
            field = value
            setChangedAndNotify("firstName")
        }

    // / The last name of the user
    var lastName: String = ""
        set(value) {
            field = value
            setChangedAndNotify("lastName")
        }

    // / The age of the user
    var age: Int = 0
        set(value) {
            field = value
            setChangedAndNotify("age")
        }

    // / The image URL of the user
    var imageUrl: String = ""
        set(value) {
            field = value
            setChangedAndNotify("imageUrl")
        }

    // / The tagline of the user
    var tagline: String = ""
        set(value) {
            field = value
            setChangedAndNotify("tagline")
        }

    // / The gender of the user
    var female: Boolean = false
        set(value) {
            field = value
            setChangedAndNotify("female")
        }

    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }
}
