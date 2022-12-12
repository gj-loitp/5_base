package vn.loitp.app.activity.customviews.recyclerview.loopingLayout

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.bekawestberg.loopinglayout.library.LoopingLayoutManager
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_looping_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.MoviesAdapter
import vn.loitp.app.common.Constants

@LogTag("LoopingLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class LoopingLayoutActivity : BaseFontActivity() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_looping_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        prepareMovieData()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/BeksOmega/looping-layout"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
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

    @SuppressLint("NotifyDataSetChanged")
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
        moviesAdapter?.notifyDataSetChanged()
    }

}
