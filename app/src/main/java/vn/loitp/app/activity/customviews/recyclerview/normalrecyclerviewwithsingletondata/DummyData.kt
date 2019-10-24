package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata

import java.util.ArrayList

import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie

/**
 * Created by www.muathu@gmail.com on 1/22/2018.
 */

class DummyData private constructor() {

    var movieList: List<Movie> = ArrayList()

    companion object {
        val instance = DummyData()
    }
}
