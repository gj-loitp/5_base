package vn.loitp.a.cv.layout

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_menu.*
import vn.loitp.R
import vn.loitp.a.cv.layout.androidSlidingUpPanel.AndroidSlidingUpPanelActivityFont
import vn.loitp.a.cv.layout.aspectratio.AspectRatioLayoutActivityFont
import vn.loitp.a.cv.layout.autoLinear.AutoLinearLayoutActivityFont
import vn.loitp.a.cv.layout.autoScrollContent.AutoScrollContentActivityFont
import vn.loitp.a.cv.layout.basket.BasketLayoutActivityFont
import vn.loitp.a.cv.layout.chess.ChessLayoutActivityFont
import vn.loitp.a.cv.layout.circularView.CircularViewActivityFont
import vn.loitp.a.cv.layout.constraint.MenuConstraintLayoutActivityFont
import vn.loitp.a.cv.layout.coordinator.MenuCoordinatorLayoutActivityFont
import vn.loitp.a.cv.layout.cornerCutLinear.CornerCutLinearLayoutActivityFont
import vn.loitp.a.cv.layout.draggablePanel.DraggablePanelActivityFont
import vn.loitp.a.cv.layout.draggablePanelFree.DraggablePanelFreeActivityFont
import vn.loitp.a.cv.layout.draggableView.DraggableViewActivityFont
import vn.loitp.a.cv.layout.expansionPanel.MenuExpansionLayoutActivityFont
import vn.loitp.a.cv.layout.floatDrag.FloatDragLayoutActivityFont
import vn.loitp.a.cv.layout.floatLayout.FloatLayoutActivityFont
import vn.loitp.a.cv.layout.flow.FlowLayoutActivityFont
import vn.loitp.a.cv.layout.greedo.GreedoLayoutActivityFont
import vn.loitp.a.cv.layout.heart.HeartLayoutActivityFont
import vn.loitp.a.cv.layout.reflection.ReflectionLayoutActivityFont
import vn.loitp.a.cv.layout.relativePopupWindow.RelativePopupWindowActivityFont
import vn.loitp.a.cv.layout.ripple.RippleLayoutActivityFont
import vn.loitp.a.cv.layout.rotate.RotateLayoutActivityFont
import vn.loitp.a.cv.layout.roundable.RoundableLayoutActivityFont
import vn.loitp.a.cv.layout.scrollView2d.ScrollView2DActivityFont
import vn.loitp.a.cv.layout.scrollView2d.ScrollView2DAdvanceActivityFont
import vn.loitp.a.cv.layout.sequence.SequenceLayoutActivityFont
import vn.loitp.a.cv.layout.shadow.ShadowLayoutActivityFont
import vn.loitp.a.cv.layout.shapeOfView.ShapeOfViewActivityFont
import vn.loitp.a.cv.layout.splitPanel.SplitPanelLayoutActivityFont
import vn.loitp.a.cv.layout.square.SquareLayoutActivityFont
import vn.loitp.a.cv.layout.swipeBack.SwipeBackLayoutActivityFont
import vn.loitp.a.cv.layout.swipeRefresh.MenuSwipeRefreshLayoutActivityFont
import vn.loitp.a.cv.layout.swipeReveal.SwipeRevealLayoutActivityFont
import vn.loitp.a.cv.layout.transformation.TransformationActivityFont
import vn.loitp.a.cv.layout.transformation.single.TransformationSingleActivityFont
import vn.loitp.a.cv.layout.zoom.ZoomLayoutActivityFont

@LogTag("MenuLayoutActivity")
@IsFullScreen(false)
class MenuLayoutActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_menu
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuLayoutActivityFont::class.java.simpleName
        }
        btAndroidSlidingUpPanel.setOnClickListener(this)
        btAspectRatioLayout.setOnClickListener(this)
        btDraggablePanel.setOnClickListener(this)
        btDraggablePanelFree.setOnClickListener(this)
        btDraggableView.setOnClickListener(this)
        btZoomLayout.setOnClickListener(this)
        btRippleLayout.setOnClickListener(this)
        btSwipeRefreshLayout.setOnClickListener(this)
        btCircularView.setOnClickListener(this)
        btAutoLinearLayout.setOnClickListener(this)
        btConstraintLayout.setOnClickListener(this)
        btSwipebackLayout.setOnClickListener(this)
        btHeartLayout.setOnClickListener(this)
        btFloatDragLayout.setOnClickListener(this)
        btRotateLayout.setOnClickListener(this)
        btCoordinatorLayout.setOnClickListener(this)
        btSquareLayout.setOnClickListener(this)
        btRelativePopupWindow.setOnClickListener(this)
        btExpansionPanel.setOnClickListener(this)
        btScrollView2d.setOnClickListener(this)
        btScrollView2dAdvance.setOnClickListener(this)
        btSwipeRevealLayout.setOnClickListener(this)
        btRoundableLayout.setOnClickListener(this)
        btShadowLayout.setOnClickListener(this)
        btShapeOfView.setOnClickListener(this)
        btFlowLayout.setOnClickListener(this)
        btSplitPanelLayout.setOnClickListener(this)
        btTramsformationLayout.setOnClickListener(this)
        btTramsformationLayoutSingle.setOnClickListener(this)
        btChessLayout.setOnClickListener(this)
        btBasketLayoutActivity.setOnClickListener(this)
        btSequenceLayoutActivity.setOnClickListener(this)
        btReflectionLayoutActivity.setOnClickListener(this)
        btCornerCutLinearLayout.setOnClickListener(this)
        btGreedoLayout.setOnClickListener(this)
        btAutoScrollContent.setOnClickListener(this)
        btFloatLayout.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btAndroidSlidingUpPanel -> launchActivity(AndroidSlidingUpPanelActivityFont::class.java)
            btAspectRatioLayout -> launchActivity(AspectRatioLayoutActivityFont::class.java)
            btDraggablePanel -> launchActivity(DraggablePanelActivityFont::class.java)
            btDraggablePanelFree -> launchActivity(DraggablePanelFreeActivityFont::class.java)
            btDraggableView -> launchActivity(DraggableViewActivityFont::class.java)
            btZoomLayout -> launchActivity(ZoomLayoutActivityFont::class.java)
            btRippleLayout -> launchActivity(RippleLayoutActivityFont::class.java)
            btSwipeRefreshLayout -> launchActivity(MenuSwipeRefreshLayoutActivityFont::class.java)
            btCircularView -> launchActivity(CircularViewActivityFont::class.java)
            btAutoLinearLayout -> launchActivity(AutoLinearLayoutActivityFont::class.java)
            btConstraintLayout -> launchActivity(MenuConstraintLayoutActivityFont::class.java)
            btSwipebackLayout -> launchActivity(SwipeBackLayoutActivityFont::class.java)
            btHeartLayout -> launchActivity(HeartLayoutActivityFont::class.java)
            btFloatDragLayout -> launchActivity(FloatDragLayoutActivityFont::class.java)
            btRotateLayout -> launchActivity(RotateLayoutActivityFont::class.java)
            btCoordinatorLayout -> launchActivity(MenuCoordinatorLayoutActivityFont::class.java)
            btSquareLayout -> launchActivity(SquareLayoutActivityFont::class.java)
            btRelativePopupWindow -> launchActivity(RelativePopupWindowActivityFont::class.java)
            btExpansionPanel -> launchActivity(MenuExpansionLayoutActivityFont::class.java)
            btScrollView2d -> launchActivity(ScrollView2DActivityFont::class.java)
            btScrollView2dAdvance -> launchActivity(ScrollView2DAdvanceActivityFont::class.java)
            btSwipeRevealLayout -> launchActivity(SwipeRevealLayoutActivityFont::class.java)
            btShadowLayout -> launchActivity(ShadowLayoutActivityFont::class.java)
            btShapeOfView -> launchActivity(ShapeOfViewActivityFont::class.java)
            btRoundableLayout -> launchActivity(RoundableLayoutActivityFont::class.java)
            btFlowLayout -> launchActivity(FlowLayoutActivityFont::class.java)
            btSplitPanelLayout -> launchActivity(SplitPanelLayoutActivityFont::class.java)
            btTramsformationLayout -> launchActivity(TransformationActivityFont::class.java)
            btTramsformationLayoutSingle -> launchActivity(TransformationSingleActivityFont::class.java)
            btChessLayout -> launchActivity(ChessLayoutActivityFont::class.java)
            btBasketLayoutActivity -> launchActivity(BasketLayoutActivityFont::class.java)
            btSequenceLayoutActivity -> launchActivity(SequenceLayoutActivityFont::class.java)
            btReflectionLayoutActivity -> launchActivity(ReflectionLayoutActivityFont::class.java)
            btCornerCutLinearLayout -> launchActivity(CornerCutLinearLayoutActivityFont::class.java)
            btGreedoLayout -> launchActivity(GreedoLayoutActivityFont::class.java)
            btAutoScrollContent -> launchActivity(AutoScrollContentActivityFont::class.java)
            btFloatLayout -> launchActivity(FloatLayoutActivityFont::class.java)
        }
    }
}
