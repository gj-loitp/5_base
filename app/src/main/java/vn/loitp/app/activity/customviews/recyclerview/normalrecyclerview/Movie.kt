package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview

/**
 * Created by www.muathu@gmail.com on 12/8/2017.
 */

class Movie {
    var title: String? = null
    var genre: String? = null
    var year: String? = null
    var cover: String? = null

    constructor() {}

    constructor(title: String, genre: String, year: String, cover: String) {
        this.title = title
        this.genre = genre
        this.year = year
        this.cover = cover
    }
}