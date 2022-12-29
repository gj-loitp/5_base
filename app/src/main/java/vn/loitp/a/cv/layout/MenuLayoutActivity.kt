package vn.loitp.a.cv.layout

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_menu.*
import vn.loitp.R
import vn.loitp.a.cv.layout.androidSlidingUpPanel.AndroidSlidingUpPanelActivity
import vn.loitp.a.cv.layout.aspectratio.AspectRatioLayoutActivity
import vn.loitp.a.cv.layout.autoLinear.AutoLinearLayoutActivity
import vn.loitp.a.cv.layout.autoScrollContent.AutoScrollContentActivity
import vn.loitp.a.cv.layout.basket.BasketLayoutActivity
import vn.loitp.a.cv.layout.chess.ChessLayoutActivity
import vn.loitp.a.cv.layout.circularView.CircularViewActivity
import vn.loitp.a.cv.layout.constraint.MenuConstraintLayoutActivity
import vn.loitp.a.cv.layout.coordinator.MenuCoordinatorLayoutActivity
import vn.loitp.a.cv.layout.cornerCutLinear.CornerCutLinearLayoutActivity
import vn.loitp.a.cv.layout.draggablePanel.DraggablePanelActivity
import vn.loitp.a.cv.layout.draggablePanelFree.DraggablePanelFreeActivity
import vn.loitp.a.cv.layout.draggableView.DraggableViewActivity
import vn.loitp.a.cv.layout.expansionPanel.MenuExpansionLayoutActivity
import vn.loitp.a.cv.layout.floatDrag.FloatDragLayoutActivity
import vn.loitp.a.cv.layout.floatLayout.FloatLayoutActivity
import vn.loitp.a.cv.layout.flow.FlowLayoutActivity
import vn.loitp.a.cv.layout.greedo.GreedoLayoutActivity
import vn.loitp.a.cv.layout.heart.HeartLayoutActivity
import vn.loitp.a.cv.layout.reflection.ReflectionLayoutActivity
import vn.loitp.a.cv.layout.relativePopupWindow.RelativePopupWindowActivity
import vn.loitp.a.cv.layout.ripple.RippleLayoutActivity
import vn.loitp.a.cv.layout.rotate.RotateLayoutActivity
import vn.loitp.a.cv.layout.roundable.RoundableLayoutActivity
import vn.loitp.a.cv.layout.scrollView2d.ScrollView2DActivity
import vn.loitp.a.cv.layout.scrollView2d.ScrollView2DAdvanceActivity
import vn.loitp.a.cv.layout.sequence.SequenceLayoutActivity
import vn.loitp.a.cv.layout.shadow.ShadowLayoutActivity
import vn.loitp.a.cv.layout.shapeOfView.ShapeOfViewActivity
import vn.loitp.a.cv.layout.splitPanel.SplitPanelLayoutActivity
import vn.loitp.a.cv.layout.square.SquareLayoutActivity
import vn.loitp.a.cv.layout.swipeBack.SwipeBackLayoutActivity
import vn.loitp.app.a.cv.layout.swipeRefresh.MenuSwipeRefreshLayoutActivity
import vn.loitp.app.a.cv.layout.swipeReveal.SwipeRevealLayoutActivity
import vn.loitp.app.a.cv.layout.transformation.TransformationActivity
import vn.loitp.app.a.cv.layout.transformation.single.TransformationSingleActivity
import vn.loitp.app.a.cv.layout.zoom.ZoomLayoutActivity

@LogTag("MenuLayoutActivity")
@IsFullScreen(false)
class MenuLayoutActivity : BaseFontActivity(), View.OnClickListener {

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
            this.tvTitle?.text = MenuLayoutActivity::class.java.simpleName
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
            btAndroidSlidingUpPanel -> launchActivity(AndroidSlidingUpPanelActivity::class.java)
            btAspectRatioLayout -> launchActivity(AspectRatioLayoutActivity::class.java)
            btDraggablePanel -> launchActivity(DraggablePanelActivity::class.java)
            btDraggablePanelFree -> launchActivity(DraggablePanelFreeActivity::class.java)
            btDraggableView -> launchActivity(DraggableViewActivity::class.java)
            btZoomLayout -> launchActivity(ZoomLayoutActivity::class.java)
            btRippleLayout -> launchActivity(RippleLayoutActivity::class.java)
            btSwipeRefreshLayout -> launchActivity(MenuSwipeRefreshLayoutActivity::class.java)
            btCircularView -> launchActivity(CircularViewActivity::class.java)
            btAutoLinearLayout -> launchActivity(AutoLinearLayoutActivity::class.java)
            btConstraintLayout -> launchActivity(MenuConstraintLayoutActivity::class.java)
            btSwipebackLayout -> launchActivity(SwipeBackLayoutActivity::class.java)
            btHeartLayout -> launchActivity(HeartLayoutActivity::class.java)
            btFloatDragLayout -> launchActivity(FloatDragLayoutActivity::class.java)
            btRotateLayout -> launchActivity(RotateLayoutActivity::class.java)
            btCoordinatorLayout -> launchActivity(MenuCoordinatorLayoutActivity::class.java)
            btSquareLayout -> launchActivity(SquareLayoutActivity::class.java)
            btRelativePopupWindow -> launchActivity(RelativePopupWindowActivity::class.java)
            btExpansionPanel -> launchActivity(MenuExpansionLayoutActivity::class.java)
            btScrollView2d -> launchActivity(ScrollView2DActivity::class.java)
            btScrollView2dAdvance -> launchActivity(ScrollView2DAdvanceActivity::class.java)
            btSwipeRevealLayout -> launchActivity(SwipeRevealLayoutActivity::class.java)
            btShadowLayout -> launchActivity(ShadowLayoutActivity::class.java)
            btShapeOfView -> launchActivity(ShapeOfViewActivity::class.java)
            btRoundableLayout -> launchActivity(RoundableLayoutActivity::class.java)
            btFlowLayout -> launchActivity(FlowLayoutActivity::class.java)
            btSplitPanelLayout -> launchActivity(SplitPanelLayoutActivity::class.java)
            btTramsformationLayout -> launchActivity(TransformationActivity::class.java)
            btTramsformationLayoutSingle -> launchActivity(TransformationSingleActivity::class.java)
            btChessLayout -> launchActivity(ChessLayoutActivity::class.java)
            btBasketLayoutActivity -> launchActivity(BasketLayoutActivity::class.java)
            btSequenceLayoutActivity -> launchActivity(SequenceLayoutActivity::class.java)
            btReflectionLayoutActivity -> launchActivity(ReflectionLayoutActivity::class.java)
            btCornerCutLinearLayout -> launchActivity(CornerCutLinearLayoutActivity::class.java)
            btGreedoLayout -> launchActivity(GreedoLayoutActivity::class.java)
            btAutoScrollContent -> launchActivity(AutoScrollContentActivity::class.java)
            btFloatLayout -> launchActivity(FloatLayoutActivity::class.java)
        }
    }
}
