package vn.loitp.up.a.cv.layout.swipeRefresh.withRv

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setColorForSwipeRefreshLayout
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ALayoutSwipeRefreshRvBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie
import vn.loitp.up.a.cv.rv.normalRv.MoviesAdapter
import vn.loitp.up.common.Constants.Companion.URL_IMG

@LogTag("SwipeRefreshLayoutRecyclerViewActivity")
@IsFullScreen(false)
class SwipeRefreshLayoutRecyclerViewActivity : BaseActivityFont() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var mAdapter: MoviesAdapter? = null
    private lateinit var binding: ALayoutSwipeRefreshRvBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutSwipeRefreshRvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = SwipeRefreshLayoutRecyclerViewActivity::class.java.simpleName
        }
        binding.swipeRefreshLayout.setOnRefreshListener { refresh() }
        binding.swipeRefreshLayout.setColorForSwipeRefreshLayout()

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
        binding.rv.layoutManager = layoutManager
        binding.rv.itemAnimator = DefaultItemAnimator()
        binding.rv.adapter = mAdapter

        prepareMovieData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refresh() {
        movieList.clear()
        mAdapter?.notifyDataSetChanged()
        setDelay(3000) {
            prepareMovieData()
            binding.swipeRefreshLayout.isRefreshing = false
            showShortInformation("Finish refresh", true)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        binding.swipeRefreshLayout.isRefreshing = true
        setDelay(2000) {
            binding.swipeRefreshLayout.isRefreshing = false
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
