package vn.loitp.app.a.cv.recyclerview

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_recycler_view.*
import vn.loitp.R
import vn.loitp.app.a.cv.layout.greedoLayout.GreedoLayoutActivity
import vn.loitp.app.a.cv.recyclerview.arcView.ArcViewActivity
import vn.loitp.app.a.cv.recyclerview.book.BookViewActivity
import vn.loitp.app.a.cv.recyclerview.carouselRecyclerview.CarouselRecyclerViewActivity
import vn.loitp.app.a.cv.recyclerview.concatAdapter.ConcatAdapterActivity
import vn.loitp.app.a.cv.recyclerview.diffUtil.DiffUtilActivity
import vn.loitp.app.a.cv.recyclerview.dragAndDropDemo.DragAndDropDemoActivity
import vn.loitp.app.a.cv.recyclerview.dragDrop.MainActivityDragDrop
import vn.loitp.app.a.cv.recyclerview.dragDropSwipe.DragDropSwipeGridRecyclerviewActivity
import vn.loitp.app.a.cv.recyclerview.dragDropSwipe.DragDropSwipeListHorizontalRecyclerviewActivity
import vn.loitp.app.a.cv.recyclerview.dragDropSwipe.DragDropSwipeListVerticalRecyclerviewActivity
import vn.loitp.app.a.cv.recyclerview.fastScroll.MenuFastScrollActivity
import vn.loitp.app.a.cv.recyclerview.fastScrollSeekbar.RecyclerViewFastScrollSeekbarActivity
import vn.loitp.app.a.cv.recyclerview.fitGridView.FitGridViewActivity
import vn.loitp.app.a.cv.recyclerview.footer.RecyclerViewFooterActivity
import vn.loitp.app.a.cv.recyclerview.footer2.RecyclerViewFooter2Activity
import vn.loitp.app.a.cv.recyclerview.galleryLayoutManager.GalleryLayoutManagerHorizontalActivity
import vn.loitp.app.a.cv.recyclerview.galleryLayoutManager.GalleryLayoutManagerVerticalActivity
import vn.loitp.app.a.cv.recyclerview.loopingLayout.LoopingLayoutActivity
import vn.loitp.app.a.cv.recyclerview.netView.NetViewActivity
import vn.loitp.app.a.cv.recyclerview.normalRecyclerView.RecyclerViewActivity
import vn.loitp.app.a.cv.recyclerview.normalRecyclerViewWithSingletonData.RecyclerViewWithSingletonDataActivity
import vn.loitp.app.a.cv.recyclerview.normalWithSpanSize.RecyclerViewWithSpanSizeActivity
import vn.loitp.app.a.cv.recyclerview.parallaxRecyclerView.ParallaxRecyclerViewActivity
import vn.loitp.app.a.cv.recyclerview.recyclerTabLayout.MenuRecyclerTabLayoutActivity
import vn.loitp.app.a.cv.recyclerview.turnLayoutManager.TurnLayoutManagerActivity

@LogTag("MenuRecyclerViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRecyclerViewActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuRecyclerViewActivity::class.java.simpleName
        }
        btArcViewActivity.setOnClickListener(this)
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
        btTurnLayoutManagerActivity.setOnClickListener(this)
        btCarouselRecyclerViewActivity.setOnClickListener(this)
        btDragDrop.setOnClickListener(this)
        btDragAndDropDemoActivity.setOnClickListener(this)
        btLoopingLayout.setOnClickListener(this)
        btGreedoLayout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btArcViewActivity -> launchActivity(ArcViewActivity::class.java)
            btParallaxRecyclerView -> launchActivity(ParallaxRecyclerViewActivity::class.java)
            btNormalRecyclerView -> launchActivity(RecyclerViewActivity::class.java)
            btNormalRecyclerViewWithSpanSize -> launchActivity(RecyclerViewWithSpanSizeActivity::class.java)
            btNormalRecyclerViewWithSingletonData -> launchActivity(
                RecyclerViewWithSingletonDataActivity::class.java
            )
            btGalleryLayoutManager -> launchActivity(GalleryLayoutManagerHorizontalActivity::class.java)
            btGalleryLayoutManagerVertical -> launchActivity(GalleryLayoutManagerVerticalActivity::class.java)
            btBookView -> launchActivity(BookViewActivity::class.java)
            btDiffUtil -> launchActivity(DiffUtilActivity::class.java)
            btRecyclerTabLayout -> launchActivity(MenuRecyclerTabLayoutActivity::class.java)
            btConcatAdapter -> launchActivity(ConcatAdapterActivity::class.java)
            btFooter -> launchActivity(RecyclerViewFooterActivity::class.java)
            btFooter2 -> launchActivity(RecyclerViewFooter2Activity::class.java)
            btNetView -> launchActivity(NetViewActivity::class.java)
            btFitGridView -> launchActivity(FitGridViewActivity::class.java)
            btDragDropSwipeRecyclerviewListVertical -> launchActivity(
                DragDropSwipeListVerticalRecyclerviewActivity::class.java
            )
            btDragDropSwipeRecyclerviewListHorizontal -> launchActivity(
                DragDropSwipeListHorizontalRecyclerviewActivity::class.java
            )
            btDragDropSwipeRecyclerviewGrid -> launchActivity(DragDropSwipeGridRecyclerviewActivity::class.java)
            btFastScroll -> launchActivity(MenuFastScrollActivity::class.java)
            btFastScrollSeekBar -> launchActivity(RecyclerViewFastScrollSeekbarActivity::class.java)
            btTurnLayoutManagerActivity -> launchActivity(TurnLayoutManagerActivity::class.java)
            btCarouselRecyclerViewActivity -> launchActivity(CarouselRecyclerViewActivity::class.java)
            btDragDrop -> launchActivity(MainActivityDragDrop::class.java)
            btDragAndDropDemoActivity -> launchActivity(DragAndDropDemoActivity::class.java)
            btLoopingLayout -> launchActivity(LoopingLayoutActivity::class.java)
            btGreedoLayout -> launchActivity(GreedoLayoutActivity::class.java)
        }
    }
}
