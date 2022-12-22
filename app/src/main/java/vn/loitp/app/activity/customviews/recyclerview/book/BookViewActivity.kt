package vn.loitp.app.activity.customviews.recyclerview.book

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import kotlinx.android.synthetic.main.activity_book_view.*
import vn.loitp.R
import vn.loitp.app.activity.customviews.recyclerview.normalRecyclerView.Movie
import vn.loitp.common.Constants

@LogTag("BookViewActivity")
@IsFullScreen(false)
class BookViewActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var bookAdapter: BookAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_book_view
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
            this.tvTitle?.text = BookViewActivity::class.java.simpleName
        }
        bookAdapter = BookAdapter(
            context = this, column = 3, moviesList = movieList,
            callback = object : BookAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    val isRemoved = movieList.remove(movie)
                    if (isRemoved) {
                        bookAdapter?.apply {
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, movieList.size)
                            checkData()
                        }
                    }
                }

                override fun onLoadMore() {}
            }
        )
        rv.layoutManager = GridLayoutManager(this, 3)
        bookAdapter?.let {
//            val scaleAdapter = AlphaInAnimationAdapter(it)
//            val scaleAdapter = ScaleInAnimationAdapter(it)
            val scaleAdapter = SlideInBottomAnimationAdapter(it)
//            val scaleAdapter = SlideInLeftAnimationAdapter(it)
//            val scaleAdapter = SlideInRightAnimationAdapter(it)
            scaleAdapter.setDuration(1000)
//            scaleAdapter.setInterpolator(OvershootInterpolator())
            scaleAdapter.setFirstOnly(true)
            rv.adapter = scaleAdapter
        }
        prepareMovieData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        var cover: String
        for (i in 0..99) {
            cover = if (i % 2 == 0) {
                Constants.URL_IMG_1
            } else {
                Constants.URL_IMG_2
            }
            val movie = Movie("Loitp $i", "Action & Adventure $i", "Year: $i", cover)
            movieList.add(movie)
        }
        bookAdapter?.apply {
            checkData()
            notifyDataSetChanged()
        }
    }
}
