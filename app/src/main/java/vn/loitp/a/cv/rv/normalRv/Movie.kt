package vn.loitp.a.cv.rv.normalRv

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
class Movie : BaseModel {
    var title: String? = null
    var genre: String? = null
    var year: String? = null
    var cover: String? = null
    var isBottom: Boolean = false

    constructor()

    constructor(title: String, genre: String, year: String, cover: String) {
        this.title = title
        this.genre = genre
        this.year = year
        this.cover = cover
    }
}
