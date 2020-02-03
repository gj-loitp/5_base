package vn.loitp.app.activity.customviews.layout

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_layout.*
import loitp.basemaster.R
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
import vn.loitp.app.activity.customviews.layout.fixedgridlayout.FixedGridLayoutActivity
import vn.loitp.app.activity.customviews.layout.floatdraglayout.FloatDragLayoutActivity
import vn.loitp.app.activity.customviews.layout.heartlayout.HeartLayoutActivity
import vn.loitp.app.activity.customviews.layout.motionlayout.MenuMotionLayoutActivity
import vn.loitp.app.activity.customviews.layout.relativepopupwindow.RelativePopupWindowActivity
import vn.loitp.app.activity.customviews.layout.ripplelayout.RippleLayoutActivity
import vn.loitp.app.activity.customviews.layout.rotatelayout.RotateLayoutActivity
import vn.loitp.app.activity.customviews.layout.roundablelayout.RoundableLayoutActivity
import vn.loitp.app.activity.customviews.layout.scrollview2d.ScrollView2DActivity
import vn.loitp.app.activity.customviews.layout.scrollview2d.ScrollView2DAdvanceActivity
import vn.loitp.app.activity.customviews.layout.shadowlayout.ShadowLayoutActivity
import vn.loitp.app.activity.customviews.layout.squarelayout.SquareLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipebacklayout.SwipeBackLayoutActivity
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.SwipeRefreshLayoutMenuActivity
import vn.loitp.app.activity.customviews.layout.swipereveallayout.SwipeRevealLayoutActivity
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity

class LayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        btMotionLayout.setOnClickListener(this)
        btExpansionPanel.setOnClickListener(this)
        btFixedGridLayout.setOnClickListener(this)
        btScrollView2d.setOnClickListener(this)
        btScrollView2dAdvance.setOnClickListener(this)
        btSwipeRevealLayout.setOnClickListener(this)
        btRoundableLayout.setOnClickListener(this)
        btShadowLayout.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_layout
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
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
            btMotionLayout -> intent = Intent(activity, MenuMotionLayoutActivity::class.java)
            btExpansionPanel -> intent = Intent(activity, ExpansionLayoutMenuActivity::class.java)
            btFixedGridLayout -> intent = Intent(activity, FixedGridLayoutActivity::class.java)
            btScrollView2d -> intent = Intent(activity, ScrollView2DActivity::class.java)
            btScrollView2dAdvance -> intent = Intent(activity, ScrollView2DAdvanceActivity::class.java)
            btSwipeRevealLayout -> intent = Intent(activity, SwipeRevealLayoutActivity::class.java)
            btShadowLayout -> intent = Intent(activity, ShadowLayoutActivity::class.java)
            btRoundableLayout -> intent = Intent(activity, RoundableLayoutActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
