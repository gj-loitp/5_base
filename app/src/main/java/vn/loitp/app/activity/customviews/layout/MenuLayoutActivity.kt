package vn.loitp.app.activity.customviews.layout

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_layout.*
import vn.loitp.R
import vn.loitp.app.activity.customviews.layout.androidSlidingUpPanel.AndroidSlidingUpPanelActivity
import vn.loitp.app.activity.customviews.layout.aspectratio.AspectRatioLayoutActivity
import vn.loitp.app.activity.customviews.layout.autoLinearLayout.AutoLinearLayoutActivity
import vn.loitp.app.activity.customviews.layout.autoScrollContent.AutoScrollContentActivity
import vn.loitp.app.activity.customviews.layout.basketLayout.BasketLayoutActivity
import vn.loitp.app.activity.customviews.layout.chess.ChessLayoutActivity
import vn.loitp.app.activity.customviews.layout.circularView.CircularViewActivity
import vn.loitp.app.activity.customviews.layout.constraintLayout.MenuConstraintlayoutActivity
import vn.loitp.app.activity.customviews.layout.coordinatorLayout.MenuCoordinatorLayoutActivity
import vn.loitp.app.activity.customviews.layout.cornerCutLinearLayout.CornerCutLinearLayoutActivity
import vn.loitp.app.activity.customviews.layout.draggablePanel.DraggablePanelActivity
import vn.loitp.app.activity.customviews.layout.draggablePanelFree.DraggablePanelFreeActivity
import vn.loitp.app.activity.customviews.layout.draggableView.DraggableViewActivity
import vn.loitp.app.activity.customviews.layout.expansionPanel.MenuExpansionLayoutActivity
import vn.loitp.app.activity.customviews.layout.floatDragLayout.FloatDragLayoutActivity
import vn.loitp.app.activity.customviews.layout.flowLayout.FlowLayoutActivity
import vn.loitp.app.activity.customviews.layout.greedoLayout.GreedoLayoutActivity
import vn.loitp.app.activity.customviews.layout.heartLayout.HeartLayoutActivity
import vn.loitp.app.activity.customviews.layout.reflection.ReflectionLayoutActivity
import vn.loitp.app.activity.customviews.layout.relativePopupWindow.RelativePopupWindowActivity
import vn.loitp.app.activity.customviews.layout.rippleLayout.RippleLayoutActivity
import vn.loitp.app.activity.customviews.layout.rotateLayout.RotateLayoutActivity
import vn.loitp.app.activity.customviews.layout.roundableLayout.RoundableLayoutActivity
import vn.loitp.app.activity.customviews.layout.scrollView2d.ScrollView2DActivity
import vn.loitp.app.activity.customviews.layout.scrollView2d.ScrollView2DAdvanceActivity
import vn.loitp.app.activity.customviews.layout.sequenceLayout.SequenceLayoutActivity
import vn.loitp.app.activity.customviews.layout.shadowLayout.ShadowLayoutActivity
import vn.loitp.app.activity.customviews.layout.shapeOfView.ShapeOfViewActivity
import vn.loitp.app.activity.customviews.layout.splitPanelLayout.SplitPanelLayoutActivity
import vn.loitp.app.activity.customviews.layout.squareLayout.SquareLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipeBackLayout.SwipeBackLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipeRefreshLayout.MenuSwipeRefreshLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipeRevealLayout.SwipeRevealLayoutActivity
import vn.loitp.app.activity.customviews.layout.transformationLayout.TransformationActivity
import vn.loitp.app.activity.customviews.layout.transformationLayout.single.TransformationSingleActivity
import vn.loitp.app.activity.customviews.layout.zoomLayout.ZoomLayoutActivity

@LogTag("MenuLayoutActivity")
@IsFullScreen(false)
class MenuLayoutActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_layout
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
            btConstraintLayout -> launchActivity(MenuConstraintlayoutActivity::class.java)
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
        }
    }
}
