package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.*
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData.Companion.instance
import vn.loitp.app.common.Constants

@LogTag("RecyclerViewWithSingletonDataActivity")
@IsFullScreen(false)
class RecyclerViewWithSingletonDataActivity : BaseFontActivity() {

    private var mAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = MoviesAdapter(moviesList = instance.movieList,
                callback = object : MoviesAdapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShortInformation("Click " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        val isRemoved = instance.movieList.remove(movie)
                        if (isRemoved) {
                            mAdapter?.let {
                                it.notifyItemRemoved(position)
                                it.notifyItemRangeChanged(position, instance.movieList.size)
                            }
                        }
                    }

                    override fun onLoadMore() {
                        loadMore()
                    }
                })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = mAdapter
        //LUIUtil.setPullLikeIOSVertical(rv)
        prepareMovieData()
        btSetting.setOnClickListener {
            LPopupMenu.show(
                    activity = this,
                    showOnView = it,
                    menuRes = R.menu.menu_recycler_view,
                    callback = { menuItem ->
                        tvType.text = menuItem.title.toString()
                        when (menuItem.itemId) {
                            R.id.menuLinearVertical -> {
                                val lmVertical: RecyclerView.LayoutManager = LinearLayoutManager(this@RecyclerViewWithSingletonDataActivity)
                                rv.layoutManager = lmVertical
                            }
                            R.id.menuLinearHorizontal -> {
                                val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(this@RecyclerViewWithSingletonDataActivity, LinearLayoutManager.HORIZONTAL, false)
                                rv.layoutManager = lmHorizontal
                            }
                            R.id.menuGridLayoutManager2 -> rv.layoutManager = GridLayoutManager(this@RecyclerViewWithSingletonDataActivity, 2)
                            R.id.menuGridLayoutManager3 -> rv.layoutManager = GridLayoutManager(this@RecyclerViewWithSingletonDataActivity, 3)
                            R.id.menuStaggeredGridLayoutManager2 -> rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                            R.id.menuStaggeredGridLayoutManager4 -> rv.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                        }
                    }
            )
        }
    }

    private fun loadMore() {
        LUIUtil.setDelay(mls = 2000, runnable = {
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie(title = "Add new $i", genre = "Add new $i", year = "Add new: $i", cover = Constants.URL_IMG)
                instance.movieList.add(movie)
            }
            mAdapter?.notifyDataSetChanged()
            showShortInformation("Finish loadMore")
        })
    }

    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..9) {
                val movie = Movie(title = "Loitp $i", genre = "Action & Adventure $i", year = "Year: $i", cover = Constants.URL_IMG)
                instance.movieList.add(movie)
            }
        }
        mAdapter?.notifyDataSetChanged()
    }
}
