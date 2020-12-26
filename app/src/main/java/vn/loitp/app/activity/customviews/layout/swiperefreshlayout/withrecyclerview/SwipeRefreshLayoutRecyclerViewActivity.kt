package vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview

import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_swipe_refresh_recycler_view_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter
import vn.loitp.app.common.Constants.Companion.URL_IMG
import java.util.*

@LogTag("SwipeRefreshLayoutRecyclerViewActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutRecyclerViewActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var mAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_refresh_recycler_view_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener { refresh() })
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout)

        mAdapter = MoviesAdapter(movieList, object : MoviesAdapter.Callback {
            override fun onClick(movie: Movie, position: Int) {
                showShortInformation("Click " + movie.title, true)
            }

            override fun onLongClick(movie: Movie, position: Int) {}
            override fun onLoadMore() {
                loadMore()
            }
        })
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        rv.layoutManager = layoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = mAdapter

        prepareMovieData()
    }

    private fun refresh() {
        movieList.clear()
        mAdapter?.notifyDataSetChanged()
        LUIUtil.setDelay(3000) {
            prepareMovieData()
            swipeRefreshLayout?.isRefreshing = false
            showShortInformation("Finish refresh", true)
        }
    }

    private fun loadMore() {
        swipeRefreshLayout.isRefreshing = true
        LUIUtil.setDelay(2000) {
            swipeRefreshLayout?.isRefreshing = false
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie("Add new $i", "Add new $i", "Add new: $i", URL_IMG)
                movieList.add(movie)
            }
            mAdapter?.notifyDataSetChanged()
            showShortInformation("Finish loadMore", true)
        }
    }

    private fun prepareMovieData() {
        for (i in 0..49) {
            val movie = Movie("Loitp $i", "Action & Adventure $i", "Year: $i", URL_IMG)
            movieList.add(movie)
        }
        mAdapter?.notifyDataSetChanged()
    }
}
