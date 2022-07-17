package vn.loitp.app.activity.customviews.recyclerview.normalRecyclerViewWithSingletonData

import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.Movie

class DummyData private constructor() {

    var movieList = ArrayList<Movie>()

    companion object {
        val instance = DummyData()
    }
}
