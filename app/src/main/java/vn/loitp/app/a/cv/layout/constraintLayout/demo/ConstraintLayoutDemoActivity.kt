package vn.loitp.app.a.cv.layout.constraintLayout.demo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_constraintlayout_demo.*
import vn.loitp.R

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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
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
