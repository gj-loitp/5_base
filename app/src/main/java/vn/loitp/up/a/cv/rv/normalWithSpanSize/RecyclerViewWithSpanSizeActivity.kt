package vn.loitp.up.a.cv.rv.normalWithSpanSize

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import vn.loitp.R
import vn.loitp.a.cv.rv.normalRv.Movie
import vn.loitp.a.cv.rv.normalRv.MoviesAdapter
import vn.loitp.databinding.ARvBinding
import vn.loitp.up.common.Constants

@LogTag("RecyclerViewWithSpanSizeActivity")
@IsFullScreen(false)
class RecyclerViewWithSpanSizeActivity : BaseActivityFont() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null
    private lateinit var binding: ARvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        prepareMovieData()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
        binding.rv.layoutManager = gridLayoutManager
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
            binding.rv.adapter = animAdapter
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

    private fun loadMore() {
        setDelay(
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
                moviesAdapter?.notifyAllViews()
                showShortInformation("Finish loadMore")
            }
        )
    }

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
        moviesAdapter?.notifyAllViews()
    }
}
