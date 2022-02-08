package vn.loitp.app.activity.customviews.layout.floatdraglayout

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseActivity
import com.views.layout.floatdraglayout.DisplayUtil
import com.views.layout.floatdraglayout.FloatDragLayout
import kotlinx.android.synthetic.main.activity_0.*
import vn.loitp.app.R

@LogTag("FloatDragWindowModeActivity")
@IsFullScreen(false)
class FloatDragWindowModeActivity : BaseActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        val floatDragLayout = FloatDragLayout(this)
        floatDragLayout.setBackgroundResource(R.drawable.ic_launcher_loitp)
        val size = DisplayUtil.dp2px(this, 45)
        val layoutParams = FrameLayout.LayoutParams(size, size)
        floatDragLayout.layoutParams = layoutParams
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        flWindows.addView(floatDragLayout, layoutParams)

        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons")
        }
    }
}
