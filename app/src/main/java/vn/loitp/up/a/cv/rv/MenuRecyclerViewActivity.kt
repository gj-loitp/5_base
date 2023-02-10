package vn.loitp.up.a.cv.rv

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.a.cv.layout.greedo.GreedoLayoutActivityFont
import vn.loitp.a.cv.rv.arcView.ArcViewActivity
import vn.loitp.a.cv.rv.book.BookViewActivity
import vn.loitp.a.cv.rv.carouselRv.CarouselRecyclerViewActivity
import vn.loitp.a.cv.rv.concatAdapter.ConcatAdapterActivity
import vn.loitp.a.cv.rv.diffUtil.DiffUtilActivity
import vn.loitp.a.cv.rv.dragAndDropDemo.DragAndDropDemoActivity
import vn.loitp.a.cv.rv.dragDrop.MainActivityDragDrop
import vn.loitp.a.cv.rv.dragDropSwipe.DragDropSwipeGridRecyclerviewActivity
import vn.loitp.a.cv.rv.dragDropSwipe.DragDropSwipeListHorizontalRecyclerviewActivity
import vn.loitp.a.cv.rv.dragDropSwipe.DragDropSwipeListVerticalRecyclerviewActivity
import vn.loitp.a.cv.rv.fastScroll.MenuFastScrollActivity
import vn.loitp.a.cv.rv.fastScrollSeekbar.RvFastScrollSeekbarActivity
import vn.loitp.a.cv.rv.fitGv.FitGridViewActivity
import vn.loitp.a.cv.rv.footer.RecyclerViewFooterActivity
import vn.loitp.a.cv.rv.footer2.RecyclerViewFooter2Activity
import vn.loitp.a.cv.rv.galleryLayoutManager.GalleryLayoutManagerHorizontalActivity
import vn.loitp.a.cv.rv.galleryLayoutManager.GalleryLayoutManagerVerticalActivityFont
import vn.loitp.a.cv.rv.looping.LoopingLayoutActivity
import vn.loitp.a.cv.rv.netView.NetViewActivity
import vn.loitp.a.cv.rv.normalRv.RecyclerViewActivity
import vn.loitp.a.cv.rv.normalRvWithSingletonData.RecyclerViewWithSingletonDataActivity
import vn.loitp.a.cv.rv.normalWithSpanSize.RecyclerViewWithSpanSizeActivity
import vn.loitp.a.cv.rv.parallaxRv.ParallaxRecyclerViewActivity
import vn.loitp.databinding.ARvMenuBinding
import vn.loitp.up.a.cv.rv.recyclerTabLayout.MenuRecyclerTabLayoutActivity
import vn.loitp.up.a.cv.rv.turnLayoutManager.TurnLayoutManagerActivity

@LogTag("MenuRecyclerViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRecyclerViewActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: ARvMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuRecyclerViewActivity::class.java.simpleName
        }
        binding.btArcViewActivity.setOnClickListener(this)
        binding.btParallaxRecyclerView.setOnClickListener(this)
        binding.btNormalRecyclerView.setOnClickListener(this)
        binding.btNormalRecyclerViewWithSpanSize.setOnClickListener(this)
        binding.btNormalRecyclerViewWithSingletonData.setOnClickListener(this)
        binding.btGalleryLayoutManager.setOnClickListener(this)
        binding.btGalleryLayoutManagerVertical.setOnClickListener(this)
        binding.btBookView.setOnClickListener(this)
        binding.btDiffUtil.setOnClickListener(this)
        binding.btRecyclerTabLayout.setOnClickListener(this)
        binding.btConcatAdapter.setOnClickListener(this)
        binding.btFooter.setOnClickListener(this)
        binding.btFooter2.setOnClickListener(this)
        binding.btNetView.setOnClickListener(this)
        binding.btFitGridView.setOnClickListener(this)
        binding.btDragDropSwipeRecyclerviewListVertical.setOnClickListener(this)
        binding.btDragDropSwipeRecyclerviewListHorizontal.setOnClickListener(this)
        binding.btDragDropSwipeRecyclerviewGrid.setOnClickListener(this)
        binding.btFastScroll.setOnClickListener(this)
        binding.btFastScrollSeekBar.setOnClickListener(this)
        binding.btTurnLayoutManagerActivity.setOnClickListener(this)
        binding.btCarouselRecyclerViewActivity.setOnClickListener(this)
        binding.btDragDrop.setOnClickListener(this)
        binding.btDragAndDropDemoActivity.setOnClickListener(this)
        binding.btLoopingLayout.setOnClickListener(this)
        binding.btGreedoLayout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btArcViewActivity -> launchActivity(ArcViewActivity::class.java)
            binding.btParallaxRecyclerView -> launchActivity(ParallaxRecyclerViewActivity::class.java)
            binding.btNormalRecyclerView -> launchActivity(RecyclerViewActivity::class.java)
            binding.btNormalRecyclerViewWithSpanSize -> launchActivity(
                RecyclerViewWithSpanSizeActivity::class.java
            )
            binding.btNormalRecyclerViewWithSingletonData -> launchActivity(
                RecyclerViewWithSingletonDataActivity::class.java
            )
            binding.btGalleryLayoutManager -> launchActivity(GalleryLayoutManagerHorizontalActivity::class.java)
            binding.btGalleryLayoutManagerVertical -> launchActivity(
                GalleryLayoutManagerVerticalActivityFont::class.java
            )
            binding.btBookView -> launchActivity(BookViewActivity::class.java)
            binding.btDiffUtil -> launchActivity(DiffUtilActivity::class.java)
            binding.btRecyclerTabLayout -> launchActivity(MenuRecyclerTabLayoutActivity::class.java)
            binding.btConcatAdapter -> launchActivity(ConcatAdapterActivity::class.java)
            binding.btFooter -> launchActivity(RecyclerViewFooterActivity::class.java)
            binding.btFooter2 -> launchActivity(RecyclerViewFooter2Activity::class.java)
            binding.btNetView -> launchActivity(NetViewActivity::class.java)
            binding.btFitGridView -> launchActivity(FitGridViewActivity::class.java)
            binding.btDragDropSwipeRecyclerviewListVertical -> launchActivity(
                DragDropSwipeListVerticalRecyclerviewActivity::class.java
            )
            binding.btDragDropSwipeRecyclerviewListHorizontal -> launchActivity(
                DragDropSwipeListHorizontalRecyclerviewActivity::class.java
            )
            binding.btDragDropSwipeRecyclerviewGrid -> launchActivity(
                DragDropSwipeGridRecyclerviewActivity::class.java
            )
            binding.btFastScroll -> launchActivity(MenuFastScrollActivity::class.java)
            binding.btFastScrollSeekBar -> launchActivity(RvFastScrollSeekbarActivity::class.java)
            binding.btTurnLayoutManagerActivity -> launchActivity(TurnLayoutManagerActivity::class.java)
            binding.btCarouselRecyclerViewActivity -> launchActivity(CarouselRecyclerViewActivity::class.java)
            binding.btDragDrop -> launchActivity(MainActivityDragDrop::class.java)
            binding.btDragAndDropDemoActivity -> launchActivity(DragAndDropDemoActivity::class.java)
            binding.btLoopingLayout -> launchActivity(LoopingLayoutActivity::class.java)
            binding.btGreedoLayout -> launchActivity(GreedoLayoutActivityFont::class.java)
        }
    }
}
