package vn.loitp.app.a.cv.layout.swipeRefresh.withRv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_swipe_refresh_recycler_view_layout.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.normalRv.Movie
import vn.loitp.app.a.cv.rv.normalRv.MoviesAdapter
import vn.loitp.common.Constants.Companion.URL_IMG

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

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = SwipeRefreshLayoutRecyclerViewActivity::class.java.simpleName
        }
        swipeRefreshLayout.setOnRefreshListener { refresh() }
        LUIUtil.setColorForSwipeRefreshLayout(swipeRefreshLayout)

        mAdapter = MoviesAdapter(
            movieList,
            object : MoviesAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title, true)
                }

                override fun onLongClick(movie: Movie, position: Int) {}
                override fun onLoadMore() {
                    loadMore()
                }
            }
        )
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        rv.layoutManager = layoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = mAdapter

        prepareMovieData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refresh() {
        movieList.clear()
        mAdapter?.notifyDataSetChanged()
        LUIUtil.setDelay(3000) {
            prepareMovieData()
            swipeRefreshLayout?.isRefreshing = false
            showShortInformation("Finish refresh", true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        for (i in 0..49) {
            val movie = Movie("Loitp $i", "Action & Adventure $i", "Year: $i", URL_IMG)
            movieList.add(movie)
        }
        mAdapter?.notifyDataSetChanged()
    }
}
