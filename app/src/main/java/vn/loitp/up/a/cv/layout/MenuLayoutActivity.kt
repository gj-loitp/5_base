package vn.loitp.up.a.cv.layout

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ALayoutMenuBinding
import vn.loitp.up.a.cv.layout.androidSlidingUpPanel.AndroidSlidingUpPanelActivity
import vn.loitp.up.a.cv.layout.aspectratio.AspectRatioLayoutActivity
import vn.loitp.up.a.cv.layout.autoLinear.AutoLinearLayoutActivity
import vn.loitp.up.a.cv.layout.autoScrollContent.AutoScrollContentActivity
import vn.loitp.up.a.cv.layout.basket.BasketLayoutActivity
import vn.loitp.up.a.cv.layout.chess.ChessLayoutActivity
import vn.loitp.up.a.cv.layout.circularView.CircularViewActivity
import vn.loitp.up.a.cv.layout.constraint.MenuConstraintLayoutActivity
import vn.loitp.up.a.cv.layout.coordinator.MenuCoordinatorLayoutActivity
import vn.loitp.up.a.cv.layout.cornerCutLinear.CornerCutLinearLayoutActivity
import vn.loitp.up.a.cv.layout.draggablePanel.DraggablePanelActivity
import vn.loitp.up.a.cv.layout.draggablePanelFree.DraggablePanelFreeActivity
import vn.loitp.up.a.cv.layout.draggableView.DraggableViewActivity
import vn.loitp.up.a.cv.layout.expansionPanel.MenuExpansionLayoutActivity
import vn.loitp.up.a.cv.layout.floatDrag.FloatDragLayoutActivity
import vn.loitp.up.a.cv.layout.floatLayout.FloatLayoutActivity
import vn.loitp.up.a.cv.layout.flow.FlowLayoutActivity
import vn.loitp.up.a.cv.layout.greedo.GreedoLayoutActivity
import vn.loitp.up.a.cv.layout.heart.HeartLayoutActivity
import vn.loitp.up.a.cv.layout.reflection.ReflectionLayoutActivity
import vn.loitp.up.a.cv.layout.relativePopupWindow.RelativePopupWindowActivity
import vn.loitp.up.a.cv.layout.ripple.RippleLayoutActivity
import vn.loitp.up.a.cv.layout.rotate.RotateLayoutActivity
import vn.loitp.up.a.cv.layout.roundable.RoundableLayoutActivity
import vn.loitp.up.a.cv.layout.scrollView2d.ScrollView2DActivity
import vn.loitp.up.a.cv.layout.scrollView2d.ScrollView2DAdvanceActivity
import vn.loitp.up.a.cv.layout.sequence.SequenceLayoutActivity
import vn.loitp.up.a.cv.layout.shadow.ShadowLayoutActivity
import vn.loitp.up.a.cv.layout.shapeOfView.ShapeOfViewActivity
import vn.loitp.up.a.cv.layout.splitPanel.SplitPanelLayoutActivity
import vn.loitp.up.a.cv.layout.square.SquareLayoutActivity
import vn.loitp.up.a.cv.layout.swipeBack.SwipeBackLayoutActivity
import vn.loitp.up.a.cv.layout.swipeRefresh.MenuSwipeRefreshLayoutActivity
import vn.loitp.up.a.cv.layout.swipeReveal.SwipeRevealLayoutActivity
import vn.loitp.up.a.cv.layout.transformation.TransformationActivity
import vn.loitp.up.a.cv.layout.transformation.single.TransformationSingleActivity
import vn.loitp.up.a.cv.layout.zoom.ZoomLayoutActivity

@LogTag("MenuLayoutActivity")
@IsFullScreen(false)
class MenuLayoutActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ALayoutMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuLayoutActivity::class.java.simpleName
        }
        binding.btAndroidSlidingUpPanel.setOnClickListener(this)
        binding.btAspectRatioLayout.setOnClickListener(this)
        binding.btDraggablePanel.setOnClickListener(this)
        binding.btDraggablePanelFree.setOnClickListener(this)
        binding.btDraggableView.setOnClickListener(this)
        binding.btZoomLayout.setOnClickListener(this)
        binding.btRippleLayout.setOnClickListener(this)
        binding.btSwipeRefreshLayout.setOnClickListener(this)
        binding.btCircularView.setOnClickListener(this)
        binding.btAutoLinearLayout.setOnClickListener(this)
        binding.btConstraintLayout.setOnClickListener(this)
        binding.btSwipebackLayout.setOnClickListener(this)
        binding.btHeartLayout.setOnClickListener(this)
        binding.btFloatDragLayout.setOnClickListener(this)
        binding.btRotateLayout.setOnClickListener(this)
        binding.btCoordinatorLayout.setOnClickListener(this)
        binding.btSquareLayout.setOnClickListener(this)
        binding.btRelativePopupWindow.setOnClickListener(this)
        binding.btExpansionPanel.setOnClickListener(this)
        binding.btScrollView2d.setOnClickListener(this)
        binding.btScrollView2dAdvance.setOnClickListener(this)
        binding.btSwipeRevealLayout.setOnClickListener(this)
        binding.btRoundableLayout.setOnClickListener(this)
        binding.btShadowLayout.setOnClickListener(this)
        binding.btShapeOfView.setOnClickListener(this)
        binding.btFlowLayout.setOnClickListener(this)
        binding.btSplitPanelLayout.setOnClickListener(this)
        binding.btTramsformationLayout.setOnClickListener(this)
        binding.btTramsformationLayoutSingle.setOnClickListener(this)
        binding.btChessLayout.setOnClickListener(this)
        binding.btBasketLayoutActivity.setOnClickListener(this)
        binding.btSequenceLayoutActivity.setOnClickListener(this)
        binding.btReflectionLayoutActivity.setOnClickListener(this)
        binding.btCornerCutLinearLayout.setOnClickListener(this)
        binding.btGreedoLayout.setOnClickListener(this)
        binding.btAutoScrollContent.setOnClickListener(this)
        binding.btFloatLayout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btAndroidSlidingUpPanel -> launchActivity(AndroidSlidingUpPanelActivity::class.java)
            binding.btAspectRatioLayout -> launchActivity(AspectRatioLayoutActivity::class.java)
            binding.btDraggablePanel -> launchActivity(DraggablePanelActivity::class.java)
            binding.btDraggablePanelFree -> launchActivity(DraggablePanelFreeActivity::class.java)
            binding.btDraggableView -> launchActivity(DraggableViewActivity::class.java)
            binding.btZoomLayout -> launchActivity(ZoomLayoutActivity::class.java)
            binding.btRippleLayout -> launchActivity(RippleLayoutActivity::class.java)
            binding.btSwipeRefreshLayout -> launchActivity(MenuSwipeRefreshLayoutActivity::class.java)
            binding.btCircularView -> launchActivity(CircularViewActivity::class.java)
            binding.btAutoLinearLayout -> launchActivity(AutoLinearLayoutActivity::class.java)
            binding.btConstraintLayout -> launchActivity(MenuConstraintLayoutActivity::class.java)
            binding.btSwipebackLayout -> launchActivity(SwipeBackLayoutActivity::class.java)
            binding.btHeartLayout -> launchActivity(HeartLayoutActivity::class.java)
            binding.btFloatDragLayout -> launchActivity(FloatDragLayoutActivity::class.java)
            binding.btRotateLayout -> launchActivity(RotateLayoutActivity::class.java)
            binding.btCoordinatorLayout -> launchActivity(MenuCoordinatorLayoutActivity::class.java)
            binding.btSquareLayout -> launchActivity(SquareLayoutActivity::class.java)
            binding.btRelativePopupWindow -> launchActivity(RelativePopupWindowActivity::class.java)
            binding.btExpansionPanel -> launchActivity(MenuExpansionLayoutActivity::class.java)
            binding.btScrollView2d -> launchActivity(ScrollView2DActivity::class.java)
            binding.btScrollView2dAdvance -> launchActivity(ScrollView2DAdvanceActivity::class.java)
            binding.btSwipeRevealLayout -> launchActivity(SwipeRevealLayoutActivity::class.java)
            binding.btShadowLayout -> launchActivity(ShadowLayoutActivity::class.java)
            binding.btShapeOfView -> launchActivity(ShapeOfViewActivity::class.java)
            binding.btRoundableLayout -> launchActivity(RoundableLayoutActivity::class.java)
            binding.btFlowLayout -> launchActivity(FlowLayoutActivity::class.java)
            binding.btSplitPanelLayout -> launchActivity(SplitPanelLayoutActivity::class.java)
            binding.btTramsformationLayout -> launchActivity(TransformationActivity::class.java)
            binding.btTramsformationLayoutSingle -> launchActivity(TransformationSingleActivity::class.java)
            binding.btChessLayout -> launchActivity(ChessLayoutActivity::class.java)
            binding.btBasketLayoutActivity -> launchActivity(BasketLayoutActivity::class.java)
            binding.btSequenceLayoutActivity -> launchActivity(SequenceLayoutActivity::class.java)
            binding.btReflectionLayoutActivity -> launchActivity(ReflectionLayoutActivity::class.java)
            binding.btCornerCutLinearLayout -> launchActivity(CornerCutLinearLayoutActivity::class.java)
            binding.btGreedoLayout -> launchActivity(GreedoLayoutActivity::class.java)
            binding.btAutoScrollContent -> launchActivity(AutoScrollContentActivity::class.java)
            binding.btFloatLayout -> launchActivity(FloatLayoutActivity::class.java)
        }
    }
}
