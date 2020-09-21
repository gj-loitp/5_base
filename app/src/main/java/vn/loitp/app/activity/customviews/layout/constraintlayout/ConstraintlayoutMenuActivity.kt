package vn.loitp.app.activity.customviews.layout.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_constraintlayout_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.constraintlayout.constraintset.ConstraintSetActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior.CustomBehaviorActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.demo.ConstraintlayoutDemoActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar.FabAndSnackbarActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget.FabFollowWidgetActivity

@LayoutId(R.layout.activity_constraintlayout_menu)
@LogTag("ConstraintlayoutMenuActivity")
@IsFullScreen(false)
class ConstraintlayoutMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_constraint_set.setOnClickListener(this)
        btDemo.setOnClickListener(this)
        bt_fab_n_snackbar.setOnClickListener(this)
        bt_fab_follow_widget.setOnClickListener(this)
        bt_custom_behavior.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btDemo -> intent = Intent(this, ConstraintlayoutDemoActivity::class.java)
            bt_fab_n_snackbar -> intent = Intent(this, FabAndSnackbarActivity::class.java)
            bt_fab_follow_widget -> intent = Intent(this, FabFollowWidgetActivity::class.java)
            bt_custom_behavior -> intent = Intent(this, CustomBehaviorActivity::class.java)
            bt_constraint_set -> intent = Intent(this, ConstraintSetActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
