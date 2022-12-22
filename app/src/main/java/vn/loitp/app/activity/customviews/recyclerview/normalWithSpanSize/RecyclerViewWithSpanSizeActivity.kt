package vn.loitp.app.activity.customviews.recyclerview.normalWithSpanSize

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.R
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.MoviesAdapter
import vn.loitp.common.Constants

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

        setupViews()
        prepareMovieData()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecyclerViewWithSpanSizeActivity::class.java.simpleName
        }
        moviesAdapter =
            MoviesAdapter(
                moviesList = movieList,
                callback = object : MoviesAdapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShortInformation("Click " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {}
                    override fun onLoadMore() {
                        loadMore()
                    }
                }
            )
        val gridLayoutManager = GridLayoutManager(this, 2)
        rv.layoutManager = gridLayoutManager
//        rv.itemAnimator = DefaultItemAnimator()
//        rv.adapter = moviesAdapter
        moviesAdapter?.let {
            val animAdapter = AlphaInAnimationAdapter(it)
//            val animAdapter = ScaleInAnimationAdapter(it)
//            val animAdapter = SlideInBottomAnimationAdapter(it)
//            val animAdapter = SlideInLeftAnimationAdapter(it)
//            val animAdapter = SlideInRightAnimationAdapter(it)

            animAdapter.setDuration(1000)
            animAdapter.setInterpolator(OvershootInterpolator())
            animAdapter.setFirstOnly(true)
            rv.adapter = animAdapter
        }
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 5 == 0) {
                    1
                } else 2
            }
        }
        // LUIUtil.setPullLikeIOSVertical(rv)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        LUIUtil.setDelay(
            mls = 2000,
            runnable = {
                val newSize = 5
                for (i in 0 until newSize) {
                    val movie = Movie(
                        title = "Add new $i",
                        genre = "Add new $i",
                        year = "Add new: $i",
                        cover = Constants.URL_IMG
                    )
                    movieList.add(movie)
                }
                moviesAdapter?.notifyDataSetChanged()
                showShortInformation("Finish loadMore")
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        for (i in 0..99) {
            val movie = Movie(
                title = "Loitp $i",
                genre = "Action & Adventure $i",
                year = "Year: $i",
                cover = Constants.URL_IMG
            )
            movieList.add(movie)
        }
        moviesAdapter?.notifyDataSetChanged()
    }
}
