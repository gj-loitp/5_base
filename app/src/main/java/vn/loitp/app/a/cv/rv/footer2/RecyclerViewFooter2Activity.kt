package vn.loitp.app.a.cv.rv.footer2

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
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LPopupMenu
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.rv.itemDecoration.StickyFooterItemDecoration
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.activity_recycler_view_footer_2.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.normalRv.Movie
import vn.loitp.app.a.cv.rv.normalRvWithSingletonData.DummyData.Companion.instance
import vn.loitp.common.Constants

@LogTag("RecyclerViewFooter2Activity")
@IsFullScreen(false)
class RecyclerViewFooter2Activity : BaseFontActivity() {

    private var footer2Adapter: Footer2Adapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_footer_2
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
            this.tvTitle?.text = RecyclerViewFooter2Activity::class.java.simpleName
        }
        footer2Adapter = Footer2Adapter(
            moviesList = instance.movieList,
            callback = object : Footer2Adapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    val isRemoved = instance.movieList.remove(movie)
                    if (isRemoved) {
                        footer2Adapter?.let {
                            it.notifyItemRemoved(position)
                            it.notifyItemRangeChanged(position, instance.movieList.size)
                        }
                    }
                }

                override fun onLoadMore() {
                }
            }
        )
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
//        rv.adapter = footer2Adapter
        footer2Adapter?.let {
            val animAdapter = AlphaInAnimationAdapter(it)
//            val animAdapter = ScaleInAnimationAdapter(it)
//            val animAdapter = SlideInBottomAnimationAdapter(it)
//            val animAdapter = SlideInLeftAnimationAdapter(it)
//            val animAdapter = SlideInRightAnimationAdapter(it)

            animAdapter.setDuration(1000)
            animAdapter.setInterpolator(OvershootInterpolator())
            animAdapter.setFirstOnly(true)
            rv.adapter = animAdapter
        }

        rv.addItemDecoration(StickyFooterItemDecoration())

        prepareMovieData()

        btSetting.setSafeOnClickListener {
            LPopupMenu.show(
                activity = this,
                showOnView = it,
                menuRes = R.menu.menu_recycler_view,
                callback = { menuItem ->
                    tvType.text = menuItem.title.toString()
                    when (menuItem.itemId) {
                        R.id.menuLinearVertical -> {
                            val lmVertical: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@RecyclerViewFooter2Activity)
                            rv.layoutManager = lmVertical
                        }
                        R.id.menuLinearHorizontal -> {
                            val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(
                                this@RecyclerViewFooter2Activity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            rv.layoutManager = lmHorizontal
                        }
                        R.id.menuGridLayoutManager2 ->
                            rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewFooter2Activity, 2)
                        R.id.menuGridLayoutManager3 ->
                            rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewFooter2Activity, 3)
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

        btAddMore.setSafeOnClickListener {
            loadMore()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        LDialogUtil.showProgress(progressBar)
        LUIUtil.setDelay(
            mls = 2000,
            runnable = {
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
                LDialogUtil.hideProgress(progressBar)
                footer2Adapter?.notifyDataSetChanged()
                showShortInformation("Finish loadMore")
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..3) {
                val movie = Movie(
                    title = "Loitp $i",
                    genre = "Action & Adventure $i",
                    year = "Year: $i",
                    cover = Constants.URL_IMG
                )
                instance.movieList.add(movie)
            }
        }
        footer2Adapter?.notifyDataSetChanged()
        LDialogUtil.hideProgress(progressBar)
    }
}
