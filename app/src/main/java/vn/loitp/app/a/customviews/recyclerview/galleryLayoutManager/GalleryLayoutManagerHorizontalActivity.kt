package vn.loitp.app.a.customviews.recyclerview.galleryLayoutManager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.rv.gallery.GalleryLayoutManager
import com.loitp.views.rv.gallery.GalleryLayoutManager.ItemTransformer
import kotlinx.android.synthetic.main.activity_recycler_view_menu_gallery_layout_manager.*
import vn.loitp.R
import vn.loitp.app.a.customviews.recyclerview.normalRecyclerView.Movie
import vn.loitp.app.a.customviews.recyclerview.normalRecyclerViewWithSingletonData.DummyData.Companion.instance
import kotlin.math.abs

@LogTag("GalleryLayoutManagerHorizontalActivity")
@IsFullScreen(false)
class GalleryLayoutManagerHorizontalActivity : BaseFontActivity() {
    private var mAdapter: GalleryAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_menu_gallery_layout_manager
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/BCsl/GalleryLayoutManager"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = GalleryLayoutManagerHorizontalActivity::class.java.simpleName
        }

        mAdapter = GalleryAdapter(
            context = this, moviesList = instance.movieList,
            callback = object : GalleryAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    // do nothing
                }

                override fun onLoadMore() {
                    // do nothing
                }
            }
        )
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        // rv.setAdapter(mAdapter);
        val layoutManager = GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL)
        layoutManager.attach(rv) // default selected position is 0
        // layoutManager.attach(recyclerView, 30);

        // ...
        // setup adapter for your RecycleView
        rv.adapter = mAdapter
        layoutManager.setCallbackInFling(true) // should receive callback when flinging, default is false
        layoutManager.setOnItemSelectedListener { _, _, position ->
            textView.text = position.toString() + "/" + mAdapter?.itemCount
        }

        // Apply ItemTransformer just like ViewPager
        layoutManager.setItemTransformer(ScaleTransformer())
        prepareMovieData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..49) {
                val movie = Movie(
                    title = "Loitp $i",
                    genre = "Action & Adventure $i",
                    year = "Year: $i",
                    cover = Constants.URL_IMG
                )
                instance.movieList.add(movie)
            }
        }
        mAdapter?.notifyDataSetChanged()
    }

    inner class ScaleTransformer : ItemTransformer {
        override fun transformItem(
            layoutManager: GalleryLayoutManager,
            item: View,
            fraction: Float
        ) {
            item.pivotX = item.width / 2f
            item.pivotY = item.height / 2.0f
            val scale = 1 - 0.4f * abs(fraction)
            item.scaleX = scale
            item.scaleY = scale
        }
    }
}
