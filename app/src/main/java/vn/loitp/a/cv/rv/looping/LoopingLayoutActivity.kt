package vn.loitp.a.cv.rv.looping

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_looping_layout.*
import vn.loitp.R
import vn.loitp.a.cv.rv.normalRv.Movie
import vn.loitp.a.cv.rv.normalRv.MoviesAdapter
import vn.loitp.up.common.Constants

@LogTag("LoopingLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class LoopingLayoutActivity : BaseActivityFont() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_looping_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        prepareMovieData()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/BeksOmega/looping-layout"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = LoopingLayoutActivity::class.java.simpleName
        }

        moviesAdapter =
            MoviesAdapter(
                moviesList = movieList,
                callback = object : MoviesAdapter.Callback {
                    override fun onClick(
                        movie: Movie,
                        position: Int
                    ) {
                        showShortInformation("Click " + movie.title)
                    }

                    override fun onLongClick(
                        movie: Movie,
                        position: Int
                    ) {
                    }

                    override fun onLoadMore() {
                    }
                }
            )
        rvHorizontal.apply {
            this.layoutManager = LoopingLayoutManager(
                context = this@LoopingLayoutActivity,
                orientation = LoopingLayoutManager.HORIZONTAL,
                reverseLayout = false
            )
            this.adapter = moviesAdapter
            this.setHasFixedSize(true)
        }
        rvVertical.apply {
            this.layoutManager = LoopingLayoutManager(
                context = this@LoopingLayoutActivity,
                orientation = LoopingLayoutManager.VERTICAL,
                reverseLayout = false
            )
            this.adapter = moviesAdapter
            this.setHasFixedSize(true)
        }
    }

    private fun prepareMovieData() {
        for (i in 0..5) {
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
