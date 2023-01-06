package vn.loitp.a.cv.layout.constraint

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_constraint_layout.*
import vn.loitp.R
import vn.loitp.a.cv.layout.constraint.constraintSet.ConstraintSetActivityFont
import vn.loitp.a.cv.layout.constraint.customBehavior.CustomBehaviorActivityFont
import vn.loitp.a.cv.layout.constraint.demo.ConstraintLayoutDemoActivityFont
import vn.loitp.a.cv.layout.constraint.fabAndSnackbar.FabAndSnackBarActivityFont
import vn.loitp.a.cv.layout.constraint.fabFollowsWidget.FabFollowWidgetActivityFont

@LogTag("MenuConstraintlayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuConstraintLayoutActivityFont : BaseActivityFont(), OnClickListener {

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
            this.tvTitle?.text = MenuConstraintLayoutActivityFont::class.java.simpleName
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
            btDemo -> intent = Intent(this, ConstraintLayoutDemoActivityFont::class.java)
            btFabAndSnackBar -> intent = Intent(this, FabAndSnackBarActivityFont::class.java)
            btFabFollowWidget -> intent = Intent(this, FabFollowWidgetActivityFont::class.java)
            btCustomBehavior -> intent = Intent(this, CustomBehaviorActivityFont::class.java)
            btConstraintSet -> intent = Intent(this, ConstraintSetActivityFont::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}
