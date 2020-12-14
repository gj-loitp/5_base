package vn.loitp.app.activity.customviews.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_view_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.bookview.BookViewActivity
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.ConcatAdapterActivity
import vn.loitp.app.activity.customviews.recyclerview.diffutil.DiffUtilActivity
import vn.loitp.app.activity.customviews.recyclerview.fitgridview.FitGridViewActivity
import vn.loitp.app.activity.customviews.recyclerview.footer.RecyclerViewFooterActivity
import vn.loitp.app.activity.customviews.recyclerview.footer2.RecyclerViewFooter2Activity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerHorizontalActivity
import vn.loitp.app.activity.customviews.recyclerview.gallerylayoutmanager.GalleryLayoutManagerVerticalActivity
import vn.loitp.app.activity.customviews.recyclerview.netview.NetViewActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.RecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.RecyclerViewWithSingletonDataActivity
import vn.loitp.app.activity.customviews.recyclerview.normalwithspansize.RecyclerViewWithSpanSizeActivity
import vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview.ParallaxRecyclerViewActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.RecyclerTabLayoutMenuActivity

@LogTag("RecyclerViewMenuActivity")
@IsFullScreen(false)
class RecyclerViewMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btParallaxRecyclerView.setOnClickListener(this)
        btNormalRecyclerView.setOnClickListener(this)
        btNormalRecyclerViewWithSpanSize.setOnClickListener(this)
        btNormalRecyclerViewWithSingletonData.setOnClickListener(this)
        btGalleryLayoutManager.setOnClickListener(this)
        btGalleryLayoutManagerVertical.setOnClickListener(this)
        btBookView.setOnClickListener(this)
        btDiffUtil.setOnClickListener(this)
        btRecyclerTabLayout.setOnClickListener(this)
        btConcatAdapter.setOnClickListener(this)
        btFooter.setOnClickListener(this)
        btFooter2.setOnClickListener(this)
        btNetView.setOnClickListener(this)
        btFitGridView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btParallaxRecyclerView -> intent = Intent(this, ParallaxRecyclerViewActivity::class.java)
            btNormalRecyclerView -> intent = Intent(this, RecyclerViewActivity::class.java)
            btNormalRecyclerViewWithSpanSize -> intent = Intent(this, RecyclerViewWithSpanSizeActivity::class.java)
            btNormalRecyclerViewWithSingletonData -> intent = Intent(this, RecyclerViewWithSingletonDataActivity::class.java)
            btGalleryLayoutManager -> intent = Intent(this, GalleryLayoutManagerHorizontalActivity::class.java)
            btGalleryLayoutManagerVertical -> intent = Intent(this, GalleryLayoutManagerVerticalActivity::class.java)
            btBookView -> intent = Intent(this, BookViewActivity::class.java)
            btDiffUtil -> intent = Intent(this, DiffUtilActivity::class.java)
            btRecyclerTabLayout -> intent = Intent(this, RecyclerTabLayoutMenuActivity::class.java)
            btConcatAdapter -> intent = Intent(this, ConcatAdapterActivity::class.java)
            btFooter -> intent = Intent(this, RecyclerViewFooterActivity::class.java)
            btFooter2 -> intent = Intent(this, RecyclerViewFooter2Activity::class.java)
            btNetView -> intent = Intent(this, NetViewActivity::class.java)
            btFitGridView -> intent = Intent(this, FitGridViewActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
