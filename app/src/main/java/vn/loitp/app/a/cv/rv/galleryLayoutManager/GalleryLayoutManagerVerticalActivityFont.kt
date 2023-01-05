package vn.loitp.app.a.cv.rv.galleryLayoutManager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.rv.gallery.GalleryLayoutManager
import com.loitp.views.rv.gallery.GalleryLayoutManager.ItemTransformer
import kotlinx.android.synthetic.main.activity_recycler_view_menu_gallery_layout_manager.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.normalRv.Movie
import vn.loitp.app.a.cv.rv.normalRvWithSingletonData.DummyData.Companion.instance
import vn.loitp.common.Constants
import kotlin.math.abs

@LogTag("GalleryLayoutManagerVerticalActivity")
@IsFullScreen(false)
class GalleryLayoutManagerVerticalActivityFont : BaseActivityFont() {
    private var galleryAdapterVertical: GalleryAdapterVertical? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_menu_gallery_layout_manager
    }

    @SuppressLint("SetTextI18n")
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
            this.tvTitle?.text = GalleryLayoutManagerVerticalActivityFont::class.java.simpleName
        }
        galleryAdapterVertical =
            GalleryAdapterVertical(
                moviesList = instance.movieList,
                callback = object : GalleryAdapterVertical.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShortInformation("onClick " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        showShortInformation("onLongClick " + movie.title)
                    }

                    override fun onLoadMore() {
                        // do nothing
                    }
                }
            )
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        // recyclerView.setAdapter(mAdapter);
        val layoutManager = GalleryLayoutManager(GalleryLayoutManager.VERTICAL)
        // layoutManager.attach(recyclerView);  //default selected position is 0
        layoutManager.attach(rv, 5)

        // ...
        // setup adapter for your RecycleView
        rv.adapter = galleryAdapterVertical
        layoutManager.setCallbackInFling(true) // should receive callback when flinging, default is false

        layoutManager.setOnItemSelectedListener { _: RecyclerView?, _: View?, position: Int ->
            @SuppressLint("SetTextI18n")
            textView.text = position.toString() + "/" + galleryAdapterVertical?.itemCount
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
                    title = "Menu $i",
                    genre = "Action & Adventure $i",
                    year = "Year: $i",
                    cover = Constants.URL_IMG
                )
                instance.movieList.add(movie)
            }
        }
        galleryAdapterVertical?.notifyDataSetChanged()
    }

    class ScaleTransformer : ItemTransformer {
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
