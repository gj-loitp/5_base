package vn.loitp.app.activity.customviews.layout.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.LayoutId
import com.annotation.LogTag

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.constraintlayout.constraintset.ConstraintSetActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior.CustomBehaviorActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.demo.ConstraintlayoutDemoActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar.FabAndSnackbarActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget.FabFollowWidgetActivity

@LayoutId(R.layout.activity_constraintlayout_menu)
@LogTag("ConstraintlayoutMenuActivity")
class ConstraintlayoutMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_constraint_set).setOnClickListener(this)
        findViewById<View>(R.id.btDemo).setOnClickListener(this)
        findViewById<View>(R.id.bt_fab_n_snackbar).setOnClickListener(this)
        findViewById<View>(R.id.bt_fab_follow_widget).setOnClickListener(this)
        findViewById<View>(R.id.bt_custom_behavior).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.btDemo -> intent = Intent(activity, ConstraintlayoutDemoActivity::class.java)
            R.id.bt_fab_n_snackbar -> intent = Intent(activity, FabAndSnackbarActivity::class.java)
            R.id.bt_fab_follow_widget -> intent = Intent(activity, FabFollowWidgetActivity::class.java)
            R.id.bt_custom_behavior -> intent = Intent(activity, CustomBehaviorActivity::class.java)
            R.id.bt_constraint_set -> intent = Intent(activity, ConstraintSetActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}
