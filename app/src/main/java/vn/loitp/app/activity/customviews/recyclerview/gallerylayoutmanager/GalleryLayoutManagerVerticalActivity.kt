package vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFontActivity
import com.views.recyclerview.gallery.GalleryLayoutManager
import com.views.recyclerview.gallery.GalleryLayoutManager.ItemTransformer
import kotlinx.android.synthetic.main.activity_recycler_view_menu_gallery_layout_manager.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData.Companion.instance
import vn.loitp.app.common.Constants.URL_IMG

//https://github.com/BCsl/GalleryLayoutManager
class GalleryLayoutManagerVerticalActivity : BaseFontActivity() {
    private var mAdapter: GalleryAdapterVertical? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = GalleryAdapterVertical(context = activity, moviesList = instance.movieList,
                callback = object : GalleryAdapterVertical.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShort("onClick " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        showShort("onLongClick " + movie.title)
                    }

                    override fun onLoadMore() {
                        //do nothing
                    }
                })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rv.layoutManager = mLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        //recyclerView.setAdapter(mAdapter);
        val layoutManager = GalleryLayoutManager(GalleryLayoutManager.VERTICAL)
        //layoutManager.attach(recyclerView);  //default selected position is 0
        layoutManager.attach(rv, 5)

        //...
        //setup adapter for your RecycleView
        rv.adapter = mAdapter
        layoutManager.setCallbackInFling(true) //should receive callback when flinging, default is false
        layoutManager.setOnItemSelectedListener { _: RecyclerView?, _: View?, position: Int -> textView.text = position.toString() + "/" + mAdapter?.itemCount }

        // Apply ItemTransformer just like ViewPager
        layoutManager.setItemTransformer(ScaleTransformer())
        prepareMovieData()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_menu_gallery_layout_manager
    }

    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..49) {
                val movie = Movie(title = "Menu $i", genre = "Action & Adventure $i", year = "Year: $i", cover = URL_IMG)
                instance.movieList.add(movie)
            }
        }
        mAdapter?.notifyDataSetChanged()
    }

    inner class ScaleTransformer : ItemTransformer {
        override fun transformItem(layoutManager: GalleryLayoutManager, item: View, fraction: Float) {
            item.pivotX = item.width / 2f
            item.pivotY = item.height / 2.0f
            val scale = 1 - 0.4f * Math.abs(fraction)
            item.scaleX = scale
            item.scaleY = scale
        }
    }
}