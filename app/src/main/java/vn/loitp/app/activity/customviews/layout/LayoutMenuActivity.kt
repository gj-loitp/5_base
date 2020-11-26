package vn.loitp.app.activity.customviews.layout

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_layout_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.aspectratiolayout.AspectRatioLayoutActivity
import vn.loitp.app.activity.customviews.layout.autolinearlayout.AutoLinearLayoutActivity
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
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAspectRatioLayout -> intent = Intent(this, AspectRatioLayoutActivity::class.java)
            btDraggablePanel -> intent = Intent(this, DraggablePanelActivity::class.java)
            btDraggablePanelFree -> intent = Intent(this, DraggablePanelFreeActivity::class.java)
            btDraggableView -> intent = Intent(this, DraggableViewActivity::class.java)
            btZoomLayout -> intent = Intent(this, ZoomLayoutActivity::class.java)
            btRippleLayout -> intent = Intent(this, RippleLayoutActivity::class.java)
            btSwipeRefreshLayout -> intent = Intent(this, SwipeRefreshLayoutMenuActivity::class.java)
            btCircularView -> intent = Intent(this, CircularViewActivity::class.java)
            btAutoLinearLayout -> intent = Intent(this, AutoLinearLayoutActivity::class.java)
            btConstraintLayout -> intent = Intent(this, ConstraintlayoutMenuActivity::class.java)
            btSwipebackLayout -> intent = Intent(this, SwipeBackLayoutActivity::class.java)
            btHeartLayout -> intent = Intent(this, HeartLayoutActivity::class.java)
            btFloatDragLayout -> intent = Intent(this, FloatDragLayoutActivity::class.java)
            btRotateLayout -> intent = Intent(this, RotateLayoutActivity::class.java)
            btCoordinatorLayout -> intent = Intent(this, CoordinatorLayoutMenuActivity::class.java)
            btSquareLayout -> intent = Intent(this, SquareLayoutActivity::class.java)
            btRelativePopupWindow -> intent = Intent(this, RelativePopupWindowActivity::class.java)
            btExpansionPanel -> intent = Intent(this, ExpansionLayoutMenuActivity::class.java)
            btScrollView2d -> intent = Intent(this, ScrollView2DActivity::class.java)
            btScrollView2dAdvance -> intent = Intent(this, ScrollView2DAdvanceActivity::class.java)
            btSwipeRevealLayout -> intent = Intent(this, SwipeRevealLayoutActivity::class.java)
            btShadowLayout -> intent = Intent(this, ShadowLayoutActivity::class.java)
            btShapeOfView -> intent = Intent(this, ShapeOfViewActivity::class.java)
            btRoundableLayout -> intent = Intent(this, RoundableLayoutActivity::class.java)
            btFlowLayout -> intent = Intent(this, FlowLayoutActivity::class.java)
            btSplitPanelLayout -> intent = Intent(this, SplitPanelLayoutActivity::class.java)
            btTramsformationLayout -> intent = Intent(this, TransformationActivity::class.java)
            btTramsformationLayoutSingle -> intent = Intent(this, TransformationSingleActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
