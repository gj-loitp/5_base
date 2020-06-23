package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata

import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import java.util.*

/**
 * Created by www.muathu@gmail.com on 1/22/2018.
 */

class DummyData private constructor() {

    var movieList = ArrayList<Movie>()

    companion object {
        val instance = DummyData()
    }
}
