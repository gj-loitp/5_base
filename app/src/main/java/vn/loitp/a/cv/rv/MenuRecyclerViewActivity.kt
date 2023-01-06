package vn.loitp.a.cv.rv

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_rv_menu.*
import vn.loitp.R
import vn.loitp.a.cv.layout.greedo.GreedoLayoutActivityFont
import vn.loitp.a.cv.rv.arcView.ArcViewActivityFont
import vn.loitp.a.cv.rv.book.BookViewActivityFont
import vn.loitp.a.cv.rv.carouselRv.CarouselRecyclerViewActivityFont
import vn.loitp.a.cv.rv.concatAdapter.ConcatAdapterActivityFont
import vn.loitp.a.cv.rv.diffUtil.DiffUtilActivityFont
import vn.loitp.a.cv.rv.dragAndDropDemo.DragAndDropDemoActivityFont
import vn.loitp.a.cv.rv.dragDrop.MainActivityDragDropFont
import vn.loitp.a.cv.rv.dragDropSwipe.DragDropSwipeGridRecyclerviewActivityFont
import vn.loitp.a.cv.rv.dragDropSwipe.DragDropSwipeListHorizontalRecyclerviewActivityFont
import vn.loitp.a.cv.rv.dragDropSwipe.DragDropSwipeListVerticalRecyclerviewActivityFont
import vn.loitp.a.cv.rv.fastScroll.MenuFastScrollActivityFont
import vn.loitp.a.cv.rv.fastScrollSeekbar.RecyclerViewFastScrollSeekbarActivityFont
import vn.loitp.a.cv.rv.fitGv.FitGridViewActivityFont
import vn.loitp.a.cv.rv.footer.RecyclerViewFooterActivityFont
import vn.loitp.a.cv.rv.footer2.RecyclerViewFooter2Activity
import vn.loitp.app.a.cv.rv.galleryLayoutManager.GalleryLayoutManagerHorizontalActivityFont
import vn.loitp.app.a.cv.rv.galleryLayoutManager.GalleryLayoutManagerVerticalActivityFont
import vn.loitp.app.a.cv.rv.looping.LoopingLayoutActivityFont
import vn.loitp.app.a.cv.rv.netView.NetViewActivityFont
import vn.loitp.app.a.cv.rv.normalRv.RecyclerViewActivityFont
import vn.loitp.app.a.cv.rv.normalRvWithSingletonData.RecyclerViewWithSingletonDataActivityFont
import vn.loitp.app.a.cv.rv.normalWithSpanSize.RecyclerViewWithSpanSizeActivityFont
import vn.loitp.app.a.cv.rv.parallaxRv.ParallaxRecyclerViewActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.MenuRecyclerTabLayoutActivityFont
import vn.loitp.app.a.cv.rv.turnLayoutManager.TurnLayoutManagerActivityFont

@LogTag("MenuRecyclerViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRecyclerViewActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rv_menu
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
            btArcViewActivity -> launchActivity(ArcViewActivityFont::class.java)
            btParallaxRecyclerView -> launchActivity(ParallaxRecyclerViewActivityFont::class.java)
            btNormalRecyclerView -> launchActivity(RecyclerViewActivityFont::class.java)
            btNormalRecyclerViewWithSpanSize -> launchActivity(RecyclerViewWithSpanSizeActivityFont::class.java)
            btNormalRecyclerViewWithSingletonData -> launchActivity(
                RecyclerViewWithSingletonDataActivityFont::class.java
            )
            btGalleryLayoutManager -> launchActivity(GalleryLayoutManagerHorizontalActivityFont::class.java)
            btGalleryLayoutManagerVertical -> launchActivity(GalleryLayoutManagerVerticalActivityFont::class.java)
            btBookView -> launchActivity(BookViewActivityFont::class.java)
            btDiffUtil -> launchActivity(DiffUtilActivityFont::class.java)
            btRecyclerTabLayout -> launchActivity(MenuRecyclerTabLayoutActivityFont::class.java)
            btConcatAdapter -> launchActivity(ConcatAdapterActivityFont::class.java)
            btFooter -> launchActivity(RecyclerViewFooterActivityFont::class.java)
            btFooter2 -> launchActivity(RecyclerViewFooter2Activity::class.java)
            btNetView -> launchActivity(NetViewActivityFont::class.java)
            btFitGridView -> launchActivity(FitGridViewActivityFont::class.java)
            btDragDropSwipeRecyclerviewListVertical -> launchActivity(
                DragDropSwipeListVerticalRecyclerviewActivityFont::class.java
            )
            btDragDropSwipeRecyclerviewListHorizontal -> launchActivity(
                DragDropSwipeListHorizontalRecyclerviewActivityFont::class.java
            )
            btDragDropSwipeRecyclerviewGrid -> launchActivity(DragDropSwipeGridRecyclerviewActivityFont::class.java)
            btFastScroll -> launchActivity(MenuFastScrollActivityFont::class.java)
            btFastScrollSeekBar -> launchActivity(RecyclerViewFastScrollSeekbarActivityFont::class.java)
            btTurnLayoutManagerActivity -> launchActivity(TurnLayoutManagerActivityFont::class.java)
            btCarouselRecyclerViewActivity -> launchActivity(CarouselRecyclerViewActivityFont::class.java)
            btDragDrop -> launchActivity(MainActivityDragDropFont::class.java)
            btDragAndDropDemoActivity -> launchActivity(DragAndDropDemoActivityFont::class.java)
            btLoopingLayout -> launchActivity(LoopingLayoutActivityFont::class.java)
            btGreedoLayout -> launchActivity(GreedoLayoutActivityFont::class.java)
        }
    }
}
