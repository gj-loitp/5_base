package vn.loitp.up.a.cv.layout.floatDrag

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.layout.floatDrag.DisplayUtil
import com.loitp.views.layout.floatDrag.FloatDragLayout
import com.loitp.views.layout.floatDrag.FloatDragPopupWindow
import vn.loitp.R
import vn.loitp.databinding.AFloatDragLayoutBinding

@LogTag("FloatDragLayoutActivity")
@IsFullScreen(false)
class FloatDragLayoutActivity : BaseActivityFont(), View.OnClickListener {
    private lateinit var binding: AFloatDragLayoutBinding
    private var floatDragPopupWindow: FloatDragPopupWindow? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFloatDragLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = FloatDragLayoutActivity::class.java.simpleName
        }
        var rootView = window.decorView as ViewGroup
        rootView = rootView.findViewById(android.R.id.content)

        val floatDragLayout = FloatDragLayout(this)
        floatDragLayout.setBackgroundResource(R.drawable.ic_launcher_loitp)
        val size = DisplayUtil.dp2px(this, 45)
        val layoutParams = FrameLayout.LayoutParams(size, size)
        floatDragLayout.layoutParams = layoutParams
        layoutParams.gravity = Gravity.CENTER_VERTICAL
        rootView.addView(floatDragLayout, layoutParams)

        floatDragLayout.setOnClickListener {
            showShortInformation("Click on the hover and drag buttons", true)
        }
        binding.btChangeToFullScreen.setOnClickListener(this)
        binding.btChangeToNoTitle.setOnClickListener(this)
        binding.btChangeToWindows.setOnClickListener(this)
        binding.btShowFloatDragPopupWindow.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view) {
            binding.btChangeToFullScreen -> startActivity(
                Intent(
                    this,
                    FloatDragFullScreenActivity::class.java
                )
            )
            binding.btChangeToNoTitle -> startActivity(
                Intent(
                    this,
                    FloatDragNoTitleActivity::class.java
                )
            )
            binding.btChangeToWindows -> startActivity(
                Intent(
                    this,
                    FloatDragWindowModeActivity::class.java
                )
            )
            binding.btShowFloatDragPopupWindow -> showFloatDragPopupWindow()
        }
    }

    private fun showFloatDragPopupWindow() {
        if (floatDragPopupWindow == null) {
            val contentView = ImageView(this)
            contentView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            contentView.setImageResource(R.drawable.ic_launcher_loitp)
            floatDragPopupWindow = FloatDragPopupWindow.Builder(this)
                .setContentView(contentView)
                .setPosition(0, 300)
                .setOnClickListener { showShortInformation("Click on FloatDragPopupWindow", true) }
                .build()
        }
        floatDragPopupWindow?.show()
    }
}
