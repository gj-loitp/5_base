package vn.loitp.app.activity.customviews.layout.floatDragLayout

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.layout.floatDrag.DisplayUtil
import com.loitpcore.views.layout.floatDrag.FloatDragLayout
import kotlinx.android.synthetic.main.activity_0.*
import vn.loitp.app.R

@LogTag("FloatDragFullScreenActivity")
@IsFullScreen(false)
class FloatDragFullScreenActivity : BaseActivity() {

    private var mDecorView: View? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_0
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = FloatDragFullScreenActivity::class.java.simpleName
        }

        mDecorView = window.decorView
        val rootView = mDecorView?.findViewById<ViewGroup>(android.R.id.content)
        val floatDragLayout = FloatDragLayout(this)
        floatDragLayout.setBackgroundResource(R.drawable.ic_launcher_loitp)
        val size = DisplayUtil.dp2px(this, 45)
        val layoutParams = FrameLayout.LayoutParams(size, size)
        floatDragLayout.layoutParams = layoutParams
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        rootView?.addView(floatDragLayout, layoutParams)

        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons", true)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            mDecorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }
}
