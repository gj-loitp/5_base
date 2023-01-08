package vn.loitp.a.cv.iv.scrollParallax

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView
import com.loitp.views.iv.scrollParallax.style.HorizontalScaleStyle
import com.loitp.views.iv.scrollParallax.style.VerticalMovingStyle
import kotlinx.android.synthetic.main.a_iv_scroll_parallax.*
import vn.loitp.R

@LogTag("ScrollParallaxIvActivity")
@IsFullScreen(false)
class ScrollParallaxIvActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_iv_scroll_parallax
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/gjiazhe/ScrollParallaxImageView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ScrollParallaxIvActivityFont::class.java.simpleName
        }
        spiv.setParallaxStyles(VerticalMovingStyle()) // or other parallax styles
        for (i in 0..9) {
            val lScrollParallaxImageView = LScrollParallaxImageView(this)
            lScrollParallaxImageView.setImageResource(if (i % 2 == 0) R.drawable.iv else R.drawable.logo)
            lScrollParallaxImageView.setParallaxStyles(HorizontalScaleStyle()) // or other parallax styles
            llHorizontal.addView(lScrollParallaxImageView)
        }
        for (i in 0..19) {
            val lScrollParallaxImageView = LScrollParallaxImageView(this)
            lScrollParallaxImageView.setImageResource(if (i % 2 == 0) R.drawable.iv else R.drawable.logo)
            lScrollParallaxImageView.setParallaxStyles(VerticalMovingStyle()) // or other parallax styles
            ll.addView(lScrollParallaxImageView)
        }
    }
}
