package vn.loitp.a.cv.rv.footer

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.*
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.a_rv_footer.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.normalRv.Movie
import vn.loitp.app.a.cv.rv.normalRv.MoviesAdapter
import vn.loitp.app.a.cv.rv.normalRvWithSingletonData.DummyData.Companion.instance
import vn.loitp.common.Constants

@LogTag("RecyclerViewFooterActivity")
@IsFullScreen(false)
class RecyclerViewFooterActivityFont : BaseActivityFont() {

    private var moviesAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rv_footer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecyclerViewFooterActivityFont::class.java.simpleName
        }

        moviesAdapter = MoviesAdapter(moviesList = instance.movieList,
            callback = object : MoviesAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    val isRemoved = instance.movieList.remove(movie)
                    if (isRemoved) {
                        moviesAdapter?.let {
                            it.notifyItemRemoved(position)
                            it.notifyItemRangeChanged(position, instance.movieList.size)
                        }
                    }
                }

                override fun onLoadMore() {
                }
            })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
//        rv.adapter = moviesAdapter

        moviesAdapter?.let {
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

        prepareMovieData()

        btSetting.setSafeOnClickListener {
            this.showPopup(
                showOnView = it,
                menuRes = R.menu.menu_recycler_view,
                callback = { menuItem ->
                    tvType.text = menuItem.title.toString()
                    when (menuItem.itemId) {
                        R.id.menuLinearVertical -> {
                            val lmVertical: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@RecyclerViewFooterActivityFont)
                            rv.layoutManager = lmVertical
                        }
                        R.id.menuLinearHorizontal -> {
                            val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(
                                this@RecyclerViewFooterActivityFont,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            rv.layoutManager = lmHorizontal
                        }
                        R.id.menuGridLayoutManager2 -> rv.layoutManager =
                            GridLayoutManager(this@RecyclerViewFooterActivityFont, 2)
                        R.id.menuGridLayoutManager3 -> rv.layoutManager =
                            GridLayoutManager(this@RecyclerViewFooterActivityFont, 3)
                        R.id.menuStaggeredGridLayoutManager2 -> rv.layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        R.id.menuStaggeredGridLayoutManager4 -> rv.layoutManager =
                            StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                    }
                })
        }

        btAddMore.setSafeOnClickListener {
            loadMore()
        }
    }

    private fun loadMore() {
        progressBar.showProgress()
        setDelay(mls = 2000, runnable = {
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
            progressBar.hideProgress()
            moviesAdapter?.notifyAllViews()
            showShortInformation("Finish loadMore")
        })
    }

    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..3) {
                val movie = Movie(
                    title = "${javaClass.simpleName} $i",
                    genre = "Action & Adventure $i",
                    year = "Year: $i",
                    cover = Constants.URL_IMG
                )
                instance.movieList.add(movie)
            }
        }
        moviesAdapter?.notifyAllViews()
        progressBar.hideProgress()
    }
}
