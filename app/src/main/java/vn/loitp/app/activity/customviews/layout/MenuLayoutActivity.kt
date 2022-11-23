package vn.loitp.app.activity.customviews.layout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_layout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.aspectratio.AspectRatioLayoutActivity
import vn.loitp.app.activity.customviews.layout.autoLinearLayout.AutoLinearLayoutActivity
import vn.loitp.app.activity.customviews.layout.basketLayout.BasketLayoutActivity
import vn.loitp.app.activity.customviews.layout.chess.ChessLayoutActivity
import vn.loitp.app.activity.customviews.layout.circularView.CircularViewActivity
import vn.loitp.app.activity.customviews.layout.constraintLayout.MenuConstraintlayoutActivity
import vn.loitp.app.activity.customviews.layout.coordinatorLayout.MenuCoordinatorLayoutActivity
import vn.loitp.app.activity.customviews.layout.draggablePanel.DraggablePanelActivity
import vn.loitp.app.activity.customviews.layout.draggablePanelFree.DraggablePanelFreeActivity
import vn.loitp.app.activity.customviews.layout.draggableView.DraggableViewActivity
import vn.loitp.app.activity.customviews.layout.expansionPanel.MenuExpansionLayoutActivity
import vn.loitp.app.activity.customviews.layout.floatDragLayout.FloatDragLayoutActivity
import vn.loitp.app.activity.customviews.layout.flowLayout.FlowLayoutActivity
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuLayoutActivity::class.java.simpleName
        }
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
    }

    override fun onClick(v: View) {
        val intent = when (v) {
            btAspectRatioLayout -> Intent(this, AspectRatioLayoutActivity::class.java)
            btDraggablePanel -> Intent(this, DraggablePanelActivity::class.java)
            btDraggablePanelFree -> Intent(this, DraggablePanelFreeActivity::class.java)
            btDraggableView -> Intent(this, DraggableViewActivity::class.java)
            btZoomLayout -> Intent(this, ZoomLayoutActivity::class.java)
            btRippleLayout -> Intent(this, RippleLayoutActivity::class.java)
            btSwipeRefreshLayout -> Intent(this, MenuSwipeRefreshLayoutActivity::class.java)
            btCircularView -> Intent(this, CircularViewActivity::class.java)
            btAutoLinearLayout -> Intent(this, AutoLinearLayoutActivity::class.java)
            btConstraintLayout -> Intent(this, MenuConstraintlayoutActivity::class.java)
            btSwipebackLayout -> Intent(this, SwipeBackLayoutActivity::class.java)
            btHeartLayout -> Intent(this, HeartLayoutActivity::class.java)
            btFloatDragLayout -> Intent(this, FloatDragLayoutActivity::class.java)
            btRotateLayout -> Intent(this, RotateLayoutActivity::class.java)
            btCoordinatorLayout -> Intent(this, MenuCoordinatorLayoutActivity::class.java)
            btSquareLayout -> Intent(this, SquareLayoutActivity::class.java)
            btRelativePopupWindow -> Intent(this, RelativePopupWindowActivity::class.java)
            btExpansionPanel -> Intent(this, MenuExpansionLayoutActivity::class.java)
            btScrollView2d -> Intent(this, ScrollView2DActivity::class.java)
            btScrollView2dAdvance -> Intent(this, ScrollView2DAdvanceActivity::class.java)
            btSwipeRevealLayout -> Intent(this, SwipeRevealLayoutActivity::class.java)
            btShadowLayout -> Intent(this, ShadowLayoutActivity::class.java)
            btShapeOfView -> Intent(this, ShapeOfViewActivity::class.java)
            btRoundableLayout -> Intent(this, RoundableLayoutActivity::class.java)
            btFlowLayout -> Intent(this, FlowLayoutActivity::class.java)
            btSplitPanelLayout -> Intent(this, SplitPanelLayoutActivity::class.java)
            btTramsformationLayout -> Intent(this, TransformationActivity::class.java)
            btTramsformationLayoutSingle -> Intent(this, TransformationSingleActivity::class.java)
            btChessLayout -> Intent(this, ChessLayoutActivity::class.java)
            btBasketLayoutActivity -> Intent(this, BasketLayoutActivity::class.java)
            btSequenceLayoutActivity -> Intent(this, SequenceLayoutActivity::class.java)
            btReflectionLayoutActivity -> Intent(this, ReflectionLayoutActivity::class.java)
            else -> null
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
