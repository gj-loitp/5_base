package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview

import com.core.base.BaseModel

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

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
