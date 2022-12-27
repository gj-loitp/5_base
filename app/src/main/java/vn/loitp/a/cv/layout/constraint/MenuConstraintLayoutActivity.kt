package vn.loitp.a.cv.layout.constraint

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_constraint_layout.*
import vn.loitp.R
import vn.loitp.a.cv.layout.constraint.constraintSet.ConstraintSetActivity
import vn.loitp.a.cv.layout.constraint.customBehavior.CustomBehaviorActivity
import vn.loitp.a.cv.layout.constraint.demo.ConstraintLayoutDemoActivity
import vn.loitp.a.cv.layout.constraint.fabAndSnackbar.FabAndSnackBarActivity
import vn.loitp.a.cv.layout.constraint.fabFollowsWidget.FabFollowWidgetActivity

@LogTag("MenuConstraintlayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuConstraintLayoutActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_constraint_layout
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
            this.tvTitle?.text = MenuConstraintLayoutActivity::class.java.simpleName
        }
        btConstraintSet.setOnClickListener(this)
        btDemo.setOnClickListener(this)
        btFabAndSnackBar.setOnClickListener(this)
        btFabFollowWidget.setOnClickListener(this)
        btCustomBehavior.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btDemo -> intent = Intent(this, ConstraintLayoutDemoActivity::class.java)
            btFabAndSnackBar -> intent = Intent(this, FabAndSnackBarActivity::class.java)
            btFabFollowWidget -> intent = Intent(this, FabFollowWidgetActivity::class.java)
            btCustomBehavior -> intent = Intent(this, CustomBehaviorActivity::class.java)
            btConstraintSet -> intent = Intent(this, ConstraintSetActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
