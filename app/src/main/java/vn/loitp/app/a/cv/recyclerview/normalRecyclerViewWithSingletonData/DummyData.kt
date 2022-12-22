package vn.loitp.app.a.cv.recyclerview.normalRecyclerViewWithSingletonData

import vn.loitp.app.a.cv.recyclerview.normalRecyclerView.Movie

class DummyData private constructor() {

    var movieList = ArrayList<Movie>()

    companion object {
        val instance = DummyData()
    }
}
