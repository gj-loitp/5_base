package vn.loitp.app.a.customviews.recyclerview.normalRecyclerViewWithSingletonData

import vn.loitp.app.a.customviews.recyclerview.normalRecyclerView.Movie

class DummyData private constructor() {

    var movieList = ArrayList<Movie>()

    companion object {
        val instance = DummyData()
    }
}
