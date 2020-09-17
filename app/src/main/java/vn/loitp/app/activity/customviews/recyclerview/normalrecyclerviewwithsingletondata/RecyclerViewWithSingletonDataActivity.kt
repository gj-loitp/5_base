package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.*
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.core.utilities.LUIUtil
import com.interfaces.CallbackPopup
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData.Companion.instance
import vn.loitp.app.common.Constants

@LayoutId(R.layout.activity_recycler_view)
class RecyclerViewWithSingletonDataActivity : BaseFontActivity() {

    private var mAdapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = MoviesAdapter(moviesList = instance.movieList,
                callback = object : MoviesAdapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShort("Click " + movie.title)
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
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rv.layoutManager = mLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = mAdapter
        //LUIUtil.setPullLikeIOSVertical(rv)
        prepareMovieData()
        btSetting.setOnClickListener {
            LPopupMenu.show(activity = activity, showOnView = it, menuRes = R.menu.menu_recycler_view,
                    callBackPopup = object : CallbackPopup {
                        override fun clickOnItem(menuItem: MenuItem) {
                            tvType.text = menuItem.title.toString()
                            when (menuItem.itemId) {
                                R.id.menuLinearVertical -> {
                                    val lmVertical: RecyclerView.LayoutManager = LinearLayoutManager(activity)
                                    rv.layoutManager = lmVertical
                                }
                                R.id.menuLinearHorizontal -> {
                                    val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                                    rv.layoutManager = lmHorizontal
                                }
                                R.id.menuGridLayoutManager2 -> rv.layoutManager = GridLayoutManager(activity, 2)
                                R.id.menuGridLayoutManager3 -> rv.layoutManager = GridLayoutManager(activity, 3)
                                R.id.menuStaggeredGridLayoutManager2 -> rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                                R.id.menuStaggeredGridLayoutManager4 -> rv.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                            }
                        }
                    })
        }
    }

    private fun loadMore() {
        LUIUtil.setDelay(mls = 2000, runnable = Runnable {
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie(title = "Add new $i", genre = "Add new $i", year = "Add new: $i", cover = Constants.URL_IMG)
                instance.movieList.add(movie)
            }
            mAdapter?.notifyDataSetChanged()
            showShort("Finish loadMore")
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
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
