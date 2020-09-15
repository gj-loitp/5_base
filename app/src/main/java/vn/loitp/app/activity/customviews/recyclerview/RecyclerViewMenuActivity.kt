package vn.loitp.app.activity.customviews.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_view_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.bookview.BookViewActivity
import vn.loitp.app.activity.customviews.recyclerview.diffutil.DiffUtilActivity
import vn.loitp.app.activity.customviews.recyclerview.footer.RecyclerViewFooterActivity
import vn.loitp.app.activity.customviews.recyclerview.footer2.RecyclerViewFooter2Activity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerHorizontalActivity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerVerticalActivity
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.MergeAdapterActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.RecyclerViewWithSingletonDataActivity
import vn.loitp.app.activity.customviews.recyclerview.normalwithspansize.RecyclerViewWithSpanSizeActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid.RecyclerViewParallaxYayaActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.RecyclerTabLayoutMenuActivity

class RecyclerViewMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btParallaxRecyclerView.setOnClickListener(this)
        btNormalRecyclerView.setOnClickListener(this)
        btNormalRecyclerViewWithSpanSize.setOnClickListener(this)
        btParallaxRecyclerViewYayandroid.setOnClickListener(this)
        btNormalRecyclerViewWithSingletonData.setOnClickListener(this)
        btGalleryLayoutManager.setOnClickListener(this)
        btGalleryLayoutManagerVertical.setOnClickListener(this)
        btBookView.setOnClickListener(this)
        btDiffUtil.setOnClickListener(this)
        btRecyclerTabLayout.setOnClickListener(this)
        btMergeAdapter.setOnClickListener(this)
        btFooter.setOnClickListener(this)
        btFooter2.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btParallaxRecyclerView -> intent = Intent(activity, ParallaxRecyclerViewActivity::class.java)
            btNormalRecyclerView -> intent = Intent(activity, RecyclerViewActivity::class.java)
            btNormalRecyclerViewWithSpanSize -> intent = Intent(activity, RecyclerViewWithSpanSizeActivity::class.java)
            btParallaxRecyclerViewYayandroid -> intent = Intent(activity, RecyclerViewParallaxYayaActivity::class.java)
            btNormalRecyclerViewWithSingletonData -> intent = Intent(activity, RecyclerViewWithSingletonDataActivity::class.java)
            btGalleryLayoutManager -> intent = Intent(activity, GalleryLayoutManagerHorizontalActivity::class.java)
            btGalleryLayoutManagerVertical -> intent = Intent(activity, GalleryLayoutManagerVerticalActivity::class.java)
            btBookView -> intent = Intent(activity, BookViewActivity::class.java)
            btDiffUtil -> intent = Intent(activity, DiffUtilActivity::class.java)
            btRecyclerTabLayout -> intent = Intent(activity, RecyclerTabLayoutMenuActivity::class.java)
            btMergeAdapter -> intent = Intent(activity, MergeAdapterActivity::class.java)
            btFooter -> intent = Intent(activity, RecyclerViewFooterActivity::class.java)
            btFooter2 -> intent = Intent(activity, RecyclerViewFooter2Activity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}
