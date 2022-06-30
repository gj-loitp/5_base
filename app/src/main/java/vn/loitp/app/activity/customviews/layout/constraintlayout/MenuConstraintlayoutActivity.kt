package vn.loitp.app.activity.customviews.layout.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_constraintlayout_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.constraintlayout.constraintset.ConstraintSetActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.custombehavior.CustomBehaviorActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.demo.ConstraintLayoutDemoActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabandsnackbar.FabAndSnackBarActivity
import vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget.FabFollowWidgetActivity

@LogTag("MenuConstraintlayoutActivity")
@IsFullScreen(false)
class MenuConstraintlayoutActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_constraintlayout_menu
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
            this.tvTitle?.text = MenuConstraintlayoutActivity::class.java.simpleName
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
