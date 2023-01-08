package vn.loitp.a.cv.layout.constraint.demo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_constraint_layout_demo.*
import vn.loitp.R

@LogTag("ConstraintLayoutDemoActivity")
@IsFullScreen(false)
class ConstraintLayoutDemoActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_constraint_layout_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = ConstraintLayoutDemoActivityFont::class.java.simpleName
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
