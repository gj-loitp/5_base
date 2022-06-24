package vn.loitp.app.activity.customviews.layout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_layout_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.aspectratiolayout.AspectRatioLayoutActivity
import vn.loitp.app.activity.customviews.layout.autolinearlayout.AutoLinearLayoutActivity
import vn.loitp.app.activity.customviews.layout.basketlayout.BasketLayoutActivity
import vn.loitp.app.activity.customviews.layout.chess.ChessLayoutActivity
import vn.loitp.app.activity.customviews.layout.circularview.CircularViewActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.ConstraintlayoutMenuActivity
import vn.loitp.app.activity.customviews.layout.coordinatorlayout.CoordinatorLayoutMenuActivity
import vn.loitp.app.activity.customviews.layout.draggablepanel.DraggablePanelActivity
import vn.loitp.app.activity.customviews.layout.draggablepanelfree.DraggablePanelFreeActivity
import vn.loitp.app.activity.customviews.layout.draggableview.DraggableViewActivity
import vn.loitp.app.activity.customviews.layout.expansionpanel.ExpansionLayoutMenuActivity
import vn.loitp.app.activity.customviews.layout.floatdraglayout.FloatDragLayoutActivity
import vn.loitp.app.activity.customviews.layout.flowlayout.FlowLayoutActivity
import vn.loitp.app.activity.customviews.layout.heartlayout.HeartLayoutActivity
import vn.loitp.app.activity.customviews.layout.relativepopupwindow.RelativePopupWindowActivity
import vn.loitp.app.activity.customviews.layout.ripplelayout.RippleLayoutActivity
import vn.loitp.app.activity.customviews.layout.rotatelayout.RotateLayoutActivity
import vn.loitp.app.activity.customviews.layout.roundablelayout.RoundableLayoutActivity
import vn.loitp.app.activity.customviews.layout.scrollview2d.ScrollView2DActivity
import vn.loitp.app.activity.customviews.layout.scrollview2d.ScrollView2DAdvanceActivity
import vn.loitp.app.activity.customviews.layout.sequencelayout.SequenceLayoutActivity
import vn.loitp.app.activity.customviews.layout.shadowlayout.ShadowLayoutActivity
import vn.loitp.app.activity.customviews.layout.shapeofview.ShapeOfViewActivity
import vn.loitp.app.activity.customviews.layout.splitpanellayout.SplitPanelLayoutActivity
import vn.loitp.app.activity.customviews.layout.squarelayout.SquareLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipebacklayout.SwipeBackLayoutActivity
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.SwipeRefreshLayoutMenuActivity
import vn.loitp.app.activity.customviews.layout.swipereveallayout.SwipeRevealLayoutActivity
import vn.loitp.app.activity.customviews.layout.transformationlayout.TransformationActivity
import vn.loitp.app.activity.customviews.layout.transformationlayout.single.TransformationSingleActivity
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity

@LogTag("LayoutMenuActivity")
@IsFullScreen(false)
class LayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_menu
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
            this.tvTitle?.text = LayoutMenuActivity::class.java.simpleName
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
    }

    override fun onClick(v: View) {
        val intent = when (v) {
            btAspectRatioLayout -> Intent(this, AspectRatioLayoutActivity::class.java)
            btDraggablePanel -> Intent(this, DraggablePanelActivity::class.java)
            btDraggablePanelFree -> Intent(this, DraggablePanelFreeActivity::class.java)
            btDraggableView -> Intent(this, DraggableViewActivity::class.java)
            btZoomLayout -> Intent(this, ZoomLayoutActivity::class.java)
            btRippleLayout -> Intent(this, RippleLayoutActivity::class.java)
            btSwipeRefreshLayout -> Intent(this, SwipeRefreshLayoutMenuActivity::class.java)
            btCircularView -> Intent(this, CircularViewActivity::class.java)
            btAutoLinearLayout -> Intent(this, AutoLinearLayoutActivity::class.java)
            btConstraintLayout -> Intent(this, ConstraintlayoutMenuActivity::class.java)
            btSwipebackLayout -> Intent(this, SwipeBackLayoutActivity::class.java)
            btHeartLayout -> Intent(this, HeartLayoutActivity::class.java)
            btFloatDragLayout -> Intent(this, FloatDragLayoutActivity::class.java)
            btRotateLayout -> Intent(this, RotateLayoutActivity::class.java)
            btCoordinatorLayout -> Intent(this, CoordinatorLayoutMenuActivity::class.java)
            btSquareLayout -> Intent(this, SquareLayoutActivity::class.java)
            btRelativePopupWindow -> Intent(this, RelativePopupWindowActivity::class.java)
            btExpansionPanel -> Intent(this, ExpansionLayoutMenuActivity::class.java)
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
            else -> null
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
