package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata

import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import java.util.*

class DummyData private constructor() {

    var movieList = ArrayList<Movie>()

    companion object {
        val instance = DummyData()
    }
}
