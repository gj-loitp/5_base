package vn.loitp.up.a.cv.rv.fastScrollSeekbar

import abak.tr.com.boxedverticalseekbar.BoxedVertical
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARvFastScrollSeekBarBinding
import vn.loitp.up.a.cv.rv.normalRv.Movie
import vn.loitp.up.a.cv.rv.normalRv.MoviesAdapter
import vn.loitp.up.common.Constants

@LogTag("RecyclerViewFastScrollSeekbarActivity")
@IsFullScreen(false)
class RvFastScrollSeekbarActivity : BaseActivityFont() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null
    private var tmpPositionSeekBar = 0
    private var tmpPositionRecyclerView = 0
    private var isOnTracking = false
    private lateinit var binding: ARvFastScrollSeekBarBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvFastScrollSeekBarBinding.inflate(layoutInflater)
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RvFastScrollSeekbarActivity::class.java.simpleName
        }
        moviesAdapter = MoviesAdapter(
            moviesList = movieList,
            callback = object : MoviesAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                }

                override fun onLoadMore() {
                    loadMore()
                }
            }
        )
        val layoutManager = LinearLayoutManager(this)
        binding.rv.layoutManager = layoutManager
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!isOnTracking) {
                    val lastElementPosition = layoutManager.findLastVisibleItemPosition()
                    if (tmpPositionRecyclerView != lastElementPosition) {
                        logD("addOnScrollListener lastElementPosition $lastElementPosition")
                        binding.boxedVertical.value = movieList.size - lastElementPosition
                        tmpPositionRecyclerView = lastElementPosition
                    }
                }
            }
        })
        moviesAdapter?.let {
            binding.rv.adapter = it
        }

        binding.boxedVertical.setOnBoxedPointsChangeListener(object :
            BoxedVertical.OnValuesChangeListener {
            override fun onPointsChanged(boxedPoints: BoxedVertical, value: Int) {
                if (tmpPositionSeekBar != value) {
                    if (isOnTracking) {
                        logD("onPointsChanged $value")
                        binding.rv.scrollToPosition(movieList.size - value)
                        tmpPositionSeekBar = value
                    }
                }
            }

            override fun onStartTrackingTouch(boxedPoints: BoxedVertical) {
                logD("onStartTrackingTouch")
                isOnTracking = true
            }

            override fun onStopTrackingTouch(boxedPoints: BoxedVertical) {
                logD("onStopTrackingTouch")
                isOnTracking = false
            }
        })

        prepareMovieData()
    }

    private fun loadMore() {
        setDelay(mls = 2000, runnable = {
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
            bindSeekbarMax()
            showShortInformation("Finish loadMore")
        })
    }

    private fun prepareMovieData() {
        for (i in 0..49) {
            val movie = Movie(
                title = "${javaClass.simpleName} $i",
                genre = "Action & Adventure",
                year = "Year: 2021",
                cover = Constants.URL_IMG
            )
            movieList.add(movie)
        }
        moviesAdapter?.notifyAllViews()
        bindSeekbarMax()
    }

    private fun bindSeekbarMax() {
        binding.boxedVertical.max = movieList.size
    }
}
