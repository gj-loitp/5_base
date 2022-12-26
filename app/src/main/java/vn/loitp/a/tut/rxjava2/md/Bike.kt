package vn.loitp.a.tut.rxjava2.md

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
class Bike : BaseModel {
    var name: String? = null
    var model: String? = null
    var price: Int = 0

    constructor(
        n: String?,
        m: String?
    ) {
        name = n
        model = m
    }

    @Suppress("unused")
    constructor(
        n: String?,
        m: String?,
        p: Int
    ) {
        name = n
        model = m
        price = p
    }

    constructor()
}
