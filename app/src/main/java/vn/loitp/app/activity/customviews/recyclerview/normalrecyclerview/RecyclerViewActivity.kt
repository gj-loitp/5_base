package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.core.utilities.LUIUtil
import com.interfaces.CallbackPopup
import com.views.recyclerview.animator.adapters.ScaleInAnimationAdapter
import com.views.recyclerview.animator.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants
import java.util.*

//https://github.com/wasabeef/recyclerview-animators

@LayoutId(R.layout.activity_recycler_view)
@LogTag("RecyclerViewActivity")
class RecyclerViewActivity : BaseFontActivity() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var mAdapter: MoviesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 300
        rv.itemAnimator = animator
        //rv.getItemAnimator().setAddDuration(1000);
        btAdd3.setOnClickListener {
            val movie = Movie()
            movie.title = "Add TITLE 3"
            movie.year = "Add YEAR 3"
            movie.genre = "Add GENRE 3"
            movieList.add(index = 3, element = movie)
            mAdapter?.notifyItemInserted(3)
        }
        btRemove1.setOnClickListener {
            movieList.removeAt(index = 1)
            mAdapter?.notifyItemRemoved(1)
        }
        mAdapter = MoviesAdapter(moviesList = movieList,
                callback = object : MoviesAdapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShort("Click " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        val isRemoved = movieList.remove(movie)
                        if (isRemoved) {
                            mAdapter?.notifyItemRemoved(position)
                            mAdapter?.notifyItemRangeChanged(position, movieList.size)
                        }
                    }

                    override fun onLoadMore() {
                        loadMore()
                    }
                })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rv.layoutManager = mLayoutManager

        //rv.setAdapter(mAdapter);

        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //alphaAdapter.setDuration(1000);
        //alphaAdapter.setInterpolator(new OvershootInterpolator());
        //alphaAdapter.setFirstOnly(true);
        //recyclerView.setAdapter(alphaAdapter);

        val scaleAdapter = ScaleInAnimationAdapter(mAdapter)
        scaleAdapter.setDuration(1000)
        scaleAdapter.setInterpolator(OvershootInterpolator())
        scaleAdapter.setFirstOnly(true)
        rv.adapter = scaleAdapter
        //LUIUtil.setPullLikeIOSVertical(recyclerView = rv)
        prepareMovieData()
        btSetting.setOnClickListener {
            LPopupMenu.show(activity = activity,
                    showOnView = it,
                    menuRes = R.menu.menu_recycler_view,
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
                movieList.add(movie)
            }
            mAdapter?.notifyDataSetChanged()
            showShort("Finish loadMore")
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    private fun prepareMovieData() {
        for (i in 0..99) {
            val movie = Movie(title = "Loitp $i", genre = "Action & Adventure $i", year = "Year: $i", cover = Constants.URL_IMG)
            movieList.add(movie)
        }
        mAdapter?.notifyDataSetChanged()
    }
}
