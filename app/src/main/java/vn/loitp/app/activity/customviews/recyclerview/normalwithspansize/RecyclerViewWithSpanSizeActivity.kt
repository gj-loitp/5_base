package vn.loitp.app.activity.customviews.recyclerview.normalwithspansize

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter
import vn.loitp.app.common.Constants
import java.util.*

@LogTag("RecyclerViewWithSpanSizeActivity")
@IsFullScreen(false)
class RecyclerViewWithSpanSizeActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        moviesAdapter = MoviesAdapter(moviesList = movieList, callback = object : MoviesAdapter.Callback {
            override fun onClick(movie: Movie, position: Int) {
                showShortInformation("Click " + movie.title)
            }

            override fun onLongClick(movie: Movie, position: Int) {}
            override fun onLoadMore() {
                loadMore()
            }
        })
        val gridLayoutManager = GridLayoutManager(this, 2)
        rv.layoutManager = gridLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = moviesAdapter
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 5 == 0) {
                    1
                } else 2
            }
        }
        //LUIUtil.setPullLikeIOSVertical(rv)
        prepareMovieData()
    }

    private fun loadMore() {
        LUIUtil.setDelay(mls = 2000, runnable = Runnable {
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie(title = "Add new $i", genre = "Add new $i", year = "Add new: $i", cover = Constants.URL_IMG)
                movieList.add(movie)
            }
            moviesAdapter?.notifyDataSetChanged()
            showShortInformation("Finish loadMore")
        })
    }

    private fun prepareMovieData() {
        for (i in 0..99) {
            val movie = Movie(title = "Loitp $i", genre = "Action & Adventure $i", year = "Year: $i", cover = Constants.URL_IMG)
            movieList.add(movie)
        }
        moviesAdapter?.notifyDataSetChanged()
    }
}
