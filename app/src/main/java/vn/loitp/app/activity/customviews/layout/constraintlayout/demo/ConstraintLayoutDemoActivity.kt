package vn.loitp.app.activity.customviews.layout.constraintlayout.demo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_constraintlayout_demo.*
import vn.loitp.app.R

@LogTag("ConstraintLayoutDemoActivity")
@IsFullScreen(false)
class ConstraintLayoutDemoActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_constraintlayout_demo
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
            this.tvTitle?.text = ConstraintLayoutDemoActivity::class.java.simpleName
        }
        button.setSafeOnClickListener {
            it.visibility = View.GONE
        }
        bt0.setSafeOnClickListener {
            bt2.visibility = View.GONE
        }
        bt1.setSafeOnClickListener {
            bt2.visibility = View.VISIBLE
        }
    }
}
