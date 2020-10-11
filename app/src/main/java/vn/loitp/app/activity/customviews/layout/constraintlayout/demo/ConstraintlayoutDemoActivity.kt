package vn.loitp.app.activity.customviews.layout.constraintlayout.demo

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_constraintlayout_demo.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_constraintlayout_demo)
@LogTag("ConstraintlayoutDemoActivity")
@IsFullScreen(false)
class ConstraintlayoutDemoActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
