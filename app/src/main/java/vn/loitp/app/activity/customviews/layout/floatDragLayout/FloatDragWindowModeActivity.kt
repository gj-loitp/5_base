package vn.loitp.app.activity.customviews.layout.floatDragLayout

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.layout.floatDrag.DisplayUtil
import com.loitp.views.layout.floatDrag.FloatDragLayout
import kotlinx.android.synthetic.main.activity_0.*
import vn.loitp.R

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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FloatDragWindowModeActivity::class.java.simpleName
        }

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
