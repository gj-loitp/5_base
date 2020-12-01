package vn.loitp.app.activity.customviews.recyclerview.netview

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_net_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.bookview.BookAdapter
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants
import java.util.*

@LogTag("NetViewActivity")
@IsFullScreen(false)
class NetViewActivity : BaseFontActivity() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var netAdapter: NetAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_net_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        netAdapter = NetAdapter(context = this, column = 3, moviesList = movieList,
                callback = object : NetAdapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShortInformation("Click " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        val isRemoved = movieList.remove(movie)
                        if (isRemoved) {
                            netAdapter?.apply {
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, movieList.size)
                                checkData()
                            }
                        }
                    }

                    override fun onLoadMore() {}
                })
        rvNetView.layoutManager = GridLayoutManager(this, 3)
        rvNetView.adapter = netAdapter
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
        netAdapter?.apply {
            checkData()
            notifyDataSetChanged()
        }
    }

}
