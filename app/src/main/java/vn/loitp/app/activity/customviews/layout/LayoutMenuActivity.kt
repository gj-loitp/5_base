package vn.loitp.app.activity.customviews.layout

import android.content.Intent
import android.os.Bundle
import android.view.View

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
import vn.loitp.app.activity.customviews.layout.fixedgridlayout.FixedGridLayoutActivity
import vn.loitp.app.activity.customviews.layout.floatdraglayout.FloatDragLayoutActivity
import vn.loitp.app.activity.customviews.layout.heartlayout.HeartLayoutActivity
import vn.loitp.app.activity.customviews.layout.motionlayout.MenuMotionLayoutActivity
import vn.loitp.app.activity.customviews.layout.relativepopupwindow.RelativePopupWindowActivity
import vn.loitp.app.activity.customviews.layout.ripplelayout.RippleLayoutActivity
import vn.loitp.app.activity.customviews.layout.rotatelayout.RotateLayoutActivity
import vn.loitp.app.activity.customviews.layout.scrollview2d.ScrollView2DActivity
import vn.loitp.app.activity.customviews.layout.squarelayout.SquareLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipablelayout.SwipableLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipeablelayout.SwipeableLayoutActivity
import vn.loitp.app.activity.customviews.layout.swipebacklayout.SwipeBackLayoutActivity
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.SwipeRefreshLayoutMenuActivity
import vn.loitp.app.activity.customviews.layout.zoomlayout.ZoomLayoutActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class LayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_draggable_panel).setOnClickListener(this)
        findViewById<View>(R.id.bt_draggable_panel_free).setOnClickListener(this)
        findViewById<View>(R.id.bt_draggable_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_zoom_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_ripple_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_swipe_refresh_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_dragueur).setOnClickListener(this)
        findViewById<View>(R.id.bt_elastic_drag_dismiss_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_circular_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_auto_linear_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_swipeable_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_swipable_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_constraint_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_swipeback_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_heart_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_float_drag_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_rotate_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_coordinator_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_square_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_relative_popup_window).setOnClickListener(this)
        findViewById<View>(R.id.bt_motion_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_fixed_grid_layout).setOnClickListener(this)
        findViewById<View>(R.id.bt_scroll_view_2d).setOnClickListener(this)
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
        when (v.id) {
            R.id.bt_draggable_panel -> intent = Intent(activity, DraggablePanelActivity::class.java)
            R.id.bt_draggable_panel_free -> intent = Intent(activity, DraggablePanelFreeActivity::class.java)
            R.id.bt_draggable_view -> intent = Intent(activity, DraggableViewActivity::class.java)
            R.id.bt_zoom_layout -> intent = Intent(activity, ZoomLayoutActivity::class.java)
            R.id.bt_ripple_layout -> intent = Intent(activity, RippleLayoutActivity::class.java)
            R.id.bt_swipe_refresh_layout -> intent = Intent(activity, SwipeRefreshLayoutMenuActivity::class.java)
            R.id.bt_dragueur -> intent = Intent(activity, DragueurActivity::class.java)
            R.id.bt_elastic_drag_dismiss_layout -> intent = Intent(activity, ElasticDragDismissLayoutActivity::class.java)
            R.id.bt_circular_view -> intent = Intent(activity, CircularViewActivity::class.java)
            R.id.bt_auto_linear_layout -> intent = Intent(activity, AutoLinearLayoutActivity::class.java)
            R.id.bt_swipeable_layout -> intent = Intent(activity, SwipeableLayoutActivity::class.java)
            R.id.bt_swipable_layout -> intent = Intent(activity, SwipableLayoutActivity::class.java)
            R.id.bt_constraint_layout -> intent = Intent(activity, ConstraintlayoutMenuActivity::class.java)
            R.id.bt_swipeback_layout -> intent = Intent(activity, SwipeBackLayoutActivity::class.java)
            R.id.bt_heart_layout -> intent = Intent(activity, HeartLayoutActivity::class.java)
            R.id.bt_float_drag_layout -> intent = Intent(activity, FloatDragLayoutActivity::class.java)
            R.id.bt_rotate_layout -> intent = Intent(activity, RotateLayoutActivity::class.java)
            R.id.bt_coordinator_layout -> intent = Intent(activity, CoordinatorLayoutMenuActivity::class.java)
            R.id.bt_square_layout -> intent = Intent(activity, SquareLayoutActivity::class.java)
            R.id.bt_relative_popup_window -> intent = Intent(activity, RelativePopupWindowActivity::class.java)
            R.id.bt_motion_layout -> intent = Intent(activity, MenuMotionLayoutActivity::class.java)
            R.id.bt_fixed_grid_layout -> intent = Intent(activity, FixedGridLayoutActivity::class.java)
            R.id.bt_scroll_view_2d -> intent = Intent(activity, ScrollView2DActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
