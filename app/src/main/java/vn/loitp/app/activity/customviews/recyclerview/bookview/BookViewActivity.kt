package vn.loitp.app.activity.customviews.recyclerview.bookview

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter
import kotlinx.android.synthetic.main.activity_bookview.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants
import java.util.*

@LogTag("BookViewActivity")
@IsFullScreen(false)
class BookViewActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var bookAdapter: BookAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_bookview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookAdapter = BookAdapter(context = this, column = 3, moviesList = movieList,
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
                })
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
