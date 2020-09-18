package vn.loitp.app.activity.customviews.layout

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
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
import vn.loitp.app.activity.customviews.layout.dragueur.DragueurActivity
import vn.loitp.app.activity.customviews.layout.elasticdragdismisslayout.ElasticDragDismissLayoutActivity
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
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity

@LayoutId(R.layout.activity_layout_menu)
@LogTag("LayoutMenuActivity")
class LayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        btAspectRatioLayout.setOnClickListener(this)
        btDraggablePanel.setOnClickListener(this)
        btDraggablePanelFree.setOnClickListener(this)
        btDraggableView.setOnClickListener(this)
        btZoomLayout.setOnClickListener(this)
        btRippleLayout.setOnClickListener(this)
        btSwipeRefreshLayout.setOnClickListener(this)
        btDragueur.setOnClickListener(this)
        btElasticDragDismissLayout.setOnClickListener(this)
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
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAspectRatioLayout -> intent = Intent(activity, AspectRatioLayoutActivity::class.java)
            btDraggablePanel -> intent = Intent(activity, DraggablePanelActivity::class.java)
            btDraggablePanelFree -> intent = Intent(activity, DraggablePanelFreeActivity::class.java)
            btDraggableView -> intent = Intent(activity, DraggableViewActivity::class.java)
            btZoomLayout -> intent = Intent(activity, ZoomLayoutActivity::class.java)
            btRippleLayout -> intent = Intent(activity, RippleLayoutActivity::class.java)
            btSwipeRefreshLayout -> intent = Intent(activity, SwipeRefreshLayoutMenuActivity::class.java)
            btDragueur -> intent = Intent(activity, DragueurActivity::class.java)
            btElasticDragDismissLayout -> intent = Intent(activity, ElasticDragDismissLayoutActivity::class.java)
            btCircularView -> intent = Intent(activity, CircularViewActivity::class.java)
            btAutoLinearLayout -> intent = Intent(activity, AutoLinearLayoutActivity::class.java)
            btConstraintLayout -> intent = Intent(activity, ConstraintlayoutMenuActivity::class.java)
            btSwipebackLayout -> intent = Intent(activity, SwipeBackLayoutActivity::class.java)
            btHeartLayout -> intent = Intent(activity, HeartLayoutActivity::class.java)
            btFloatDragLayout -> intent = Intent(activity, FloatDragLayoutActivity::class.java)
            btRotateLayout -> intent = Intent(activity, RotateLayoutActivity::class.java)
            btCoordinatorLayout -> intent = Intent(activity, CoordinatorLayoutMenuActivity::class.java)
            btSquareLayout -> intent = Intent(activity, SquareLayoutActivity::class.java)
            btRelativePopupWindow -> intent = Intent(activity, RelativePopupWindowActivity::class.java)
            btExpansionPanel -> intent = Intent(activity, ExpansionLayoutMenuActivity::class.java)
            btScrollView2d -> intent = Intent(activity, ScrollView2DActivity::class.java)
            btScrollView2dAdvance -> intent = Intent(activity, ScrollView2DAdvanceActivity::class.java)
            btSwipeRevealLayout -> intent = Intent(activity, SwipeRevealLayoutActivity::class.java)
            btShadowLayout -> intent = Intent(activity, ShadowLayoutActivity::class.java)
            btShapeOfView -> intent = Intent(activity, ShapeOfViewActivity::class.java)
            btRoundableLayout -> intent = Intent(activity, RoundableLayoutActivity::class.java)
            btFlowLayout -> intent = Intent(activity, FlowLayoutActivity::class.java)
            btSplitPanelLayout -> intent = Intent(activity, SplitPanelLayoutActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
