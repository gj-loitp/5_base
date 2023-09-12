package vn.loitp.up.a.cv.iv.scrollParallax

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView
import com.loitp.views.iv.scrollParallax.style.HorizontalScaleStyle
import com.loitp.views.iv.scrollParallax.style.VerticalMovingStyle
import vn.loitp.R
import vn.loitp.databinding.AIvScrollParallaxBinding

@LogTag("ScrollParallaxIvActivity")
@IsFullScreen(false)
class ScrollParallaxIvActivity : BaseActivityFont() {
    private lateinit var binding: AIvScrollParallaxBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvScrollParallaxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/gjiazhe/ScrollParallaxImageView"
                    )
                })
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ScrollParallaxIvActivity::class.java.simpleName
        }
        binding.spiv.setParallaxStyles(VerticalMovingStyle()) // or other parallax styles
        for (i in 0..9) {
            val lScrollParallaxImageView = LScrollParallaxImageView(this)
            lScrollParallaxImageView.setImageResource(if (i % 2 == 0) R.drawable.iv else R.drawable.logo)
            lScrollParallaxImageView.setParallaxStyles(HorizontalScaleStyle()) // or other parallax styles
            binding.llHorizontal.addView(lScrollParallaxImageView)
        }
        for (i in 0..19) {
            val lScrollParallaxImageView = LScrollParallaxImageView(this)
            lScrollParallaxImageView.setImageResource(if (i % 2 == 0) R.drawable.iv else R.drawable.logo)
            lScrollParallaxImageView.setParallaxStyles(VerticalMovingStyle()) // or other parallax styles
            binding.ll.addView(lScrollParallaxImageView)
        }
    }
}
