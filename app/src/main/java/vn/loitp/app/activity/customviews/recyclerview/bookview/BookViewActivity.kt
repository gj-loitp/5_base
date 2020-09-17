package vn.loitp.app.activity.customviews.recyclerview.bookview

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_bookview.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants
import java.util.*

@LayoutId(R.layout.activity_bookview)
class BookViewActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var mAdapter: BookAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = BookAdapter(context = activity, column = 3, moviesList = movieList,
                callback = object : BookAdapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShort("Click " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        val isRemoved = movieList.remove(movie)
                        if (isRemoved) {
                            mAdapter?.apply {
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, movieList.size)
                                checkData()
                            }
                        }
                    }

                    override fun onLoadMore() {}
                })
        rv.layoutManager = GridLayoutManager(activity, 3)
        rv.adapter = mAdapter
        prepareMovieData()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
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
        mAdapter?.apply {
            checkData()
            notifyDataSetChanged()
        }
    }
}
