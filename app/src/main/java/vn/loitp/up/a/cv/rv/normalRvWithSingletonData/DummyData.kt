package vn.loitp.up.a.cv.rv.normalRvWithSingletonData

import vn.loitp.up.a.cv.rv.normalRv.Movie

class DummyData private constructor() {

    var movieList = ArrayList<Movie>()

    companion object {
        val instance = DummyData()
    }
}
