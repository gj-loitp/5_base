package vn.loitp.app.a.cv.recyclerview.normalRecyclerViewWithSingletonData

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LPopupMenu
import com.loitp.core.utilities.LUIUtil
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.R
import vn.loitp.app.a.cv.recyclerview.normalRecyclerView.Movie
import vn.loitp.app.a.cv.recyclerview.normalRecyclerView.MoviesAdapter
import vn.loitp.app.a.cv.recyclerview.normalRecyclerViewWithSingletonData.DummyData.Companion.instance
import vn.loitp.common.Constants

@LogTag("RecyclerViewWithSingletonDataActivity")
@IsFullScreen(false)
class RecyclerViewWithSingletonDataActivity : BaseFontActivity() {

    private var mAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecyclerViewWithSingletonDataActivity::class.java.simpleName
        }
        mAdapter = MoviesAdapter(
            moviesList = instance.movieList,
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
            }
        )
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
//        rv.itemAnimator = DefaultItemAnimator()
//        rv.adapter = mAdapter

        mAdapter?.let {
//            val animAdapter = AlphaInAnimationAdapter(it)
            val animAdapter = ScaleInAnimationAdapter(it)
//            val animAdapter = SlideInBottomAnimationAdapter(it)
//            val animAdapter = SlideInLeftAnimationAdapter(it)
//            val animAdapter = SlideInRightAnimationAdapter(it)

            animAdapter.setDuration(1000)
            animAdapter.setInterpolator(OvershootInterpolator())
            animAdapter.setFirstOnly(true)
            rv.adapter = animAdapter
        }

        // LUIUtil.setPullLikeIOSVertical(rv)
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
                            val lmVertical: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@RecyclerViewWithSingletonDataActivity)
                            rv.layoutManager = lmVertical
                        }
                        R.id.menuLinearHorizontal -> {
                            val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(
                                this@RecyclerViewWithSingletonDataActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            rv.layoutManager = lmHorizontal
                        }
                        R.id.menuGridLayoutManager2 ->
                            rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewWithSingletonDataActivity, 2)
                        R.id.menuGridLayoutManager3 ->
                            rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewWithSingletonDataActivity, 3)
                        R.id.menuStaggeredGridLayoutManager2 ->
                            rv.layoutManager =
                                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        R.id.menuStaggeredGridLayoutManager4 ->
                            rv.layoutManager =
                                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                    }
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        LUIUtil.setDelay(mls = 2000, runnable = {
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie(
                    title = "Add new $i",
                    genre = "Add new $i",
                    year = "Add new: $i",
                    cover = Constants.URL_IMG
                )
                instance.movieList.add(movie)
            }
            mAdapter?.notifyDataSetChanged()
            showShortInformation("Finish loadMore")
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..9) {
                val movie = Movie(
                    title = "${javaClass.simpleName} $i",
                    genre = "Action & Adventure $i",
                    year = "Year: $i",
                    cover = Constants.URL_IMG
                )
                instance.movieList.add(movie)
            }
        }
        mAdapter?.notifyDataSetChanged()
    }
}
