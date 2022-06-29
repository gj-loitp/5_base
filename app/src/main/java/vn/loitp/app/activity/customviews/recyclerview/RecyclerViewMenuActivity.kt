package vn.loitp.app.activity.customviews.recyclerview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.bookview.BookViewActivity
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.ConcatAdapterActivity
import vn.loitp.app.activity.customviews.recyclerview.diffutil.DiffUtilActivity
import vn.loitp.app.activity.customviews.recyclerview.dragdropswipe.DragDropSwipeGridRecyclerviewActivity
import vn.loitp.app.activity.customviews.recyclerview.dragdropswipe.DragDropSwipeListHorizontalRecyclerviewActivity
import vn.loitp.app.activity.customviews.recyclerview.dragdropswipe.DragDropSwipeListVerticalRecyclerviewActivity
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.FastScrollMenuActivity
import vn.loitp.app.activity.customviews.recyclerview.fastscrollseekbar.RecyclerViewFastScrollSeekbarActivity
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

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = RecyclerViewMenuActivity::class.java.simpleName
        }
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
        btDragDropSwipeRecyclerviewListVertical.setOnClickListener(this)
        btDragDropSwipeRecyclerviewListHorizontal.setOnClickListener(this)
        btDragDropSwipeRecyclerviewGrid.setOnClickListener(this)
        btFastScroll.setOnClickListener(this)
        btFastScrollSeekBar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btParallaxRecyclerView ->
                intent =
                    Intent(this, ParallaxRecyclerViewActivity::class.java)
            btNormalRecyclerView -> intent = Intent(this, RecyclerViewActivity::class.java)
            btNormalRecyclerViewWithSpanSize ->
                intent =
                    Intent(this, RecyclerViewWithSpanSizeActivity::class.java)
            btNormalRecyclerViewWithSingletonData ->
                intent =
                    Intent(this, RecyclerViewWithSingletonDataActivity::class.java)
            btGalleryLayoutManager ->
                intent =
                    Intent(this, GalleryLayoutManagerHorizontalActivity::class.java)
            btGalleryLayoutManagerVertical ->
                intent =
                    Intent(this, GalleryLayoutManagerVerticalActivity::class.java)
            btBookView -> intent = Intent(this, BookViewActivity::class.java)
            btDiffUtil -> intent = Intent(this, DiffUtilActivity::class.java)
            btRecyclerTabLayout -> intent = Intent(this, RecyclerTabLayoutMenuActivity::class.java)
            btConcatAdapter -> intent = Intent(this, ConcatAdapterActivity::class.java)
            btFooter -> intent = Intent(this, RecyclerViewFooterActivity::class.java)
            btFooter2 -> intent = Intent(this, RecyclerViewFooter2Activity::class.java)
            btNetView -> intent = Intent(this, NetViewActivity::class.java)
            btFitGridView -> intent = Intent(this, FitGridViewActivity::class.java)
            btDragDropSwipeRecyclerviewListVertical ->
                intent =
                    Intent(this, DragDropSwipeListVerticalRecyclerviewActivity::class.java)
            btDragDropSwipeRecyclerviewListHorizontal ->
                intent =
                    Intent(this, DragDropSwipeListHorizontalRecyclerviewActivity::class.java)
            btDragDropSwipeRecyclerviewGrid ->
                intent =
                    Intent(this, DragDropSwipeGridRecyclerviewActivity::class.java)
            btFastScroll -> intent = Intent(this, FastScrollMenuActivity::class.java)
            btFastScrollSeekBar ->
                intent =
                    Intent(this, RecyclerViewFastScrollSeekbarActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
