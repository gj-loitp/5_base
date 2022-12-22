package vn.loitp.app.activity.customviews.layout.floatDragLayout

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.layout.floatDrag.DisplayUtil
import com.loitp.views.layout.floatDrag.FloatDragLayout
import kotlinx.android.synthetic.main.activity_0.*
import vn.loitp.R

@LogTag("FloatDragNoTitleActivity")
@IsFullScreen(true)
class FloatDragNoTitleActivity : BaseActivity() {
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
            this.tvTitle?.text = FloatDragNoTitleActivity::class.java.simpleName
        }

        mDecorView = window.decorView
        val rootView = mDecorView?.findViewById<ViewGroup>(android.R.id.content)
        val floatDragLayout = FloatDragLayout(this)
        floatDragLayout.setBackgroundResource(R.drawable.l_heart_icon)
        val size = DisplayUtil.dp2px(this, 100)
        val layoutParams = FrameLayout.LayoutParams(size, size)
        floatDragLayout.layoutParams = layoutParams
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        rootView?.addView(floatDragLayout, layoutParams)
        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons")
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            mDecorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )
        }
    }
}
