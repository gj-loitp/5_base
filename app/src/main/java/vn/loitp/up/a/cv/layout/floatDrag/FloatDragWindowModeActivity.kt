package vn.loitp.up.a.cv.layout.floatDrag

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivity
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.floatDrag.DisplayUtil
import com.loitp.views.layout.floatDrag.FloatDragLayout
import vn.loitp.R
import vn.loitp.databinding.A0Binding

@LogTag("FloatDragWindowModeActivity")
@IsFullScreen(false)
class FloatDragWindowModeActivity : BaseActivity() {
    private lateinit var binding: A0Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = A0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
        binding.flWindows.addView(floatDragLayout, layoutParams)

        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons")
        }
    }
}
