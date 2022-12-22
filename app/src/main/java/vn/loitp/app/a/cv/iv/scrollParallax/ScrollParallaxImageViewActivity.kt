package vn.loitp.app.a.cv.iv.scrollParallax

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView
import com.loitp.views.iv.scrollParallax.style.HorizontalScaleStyle
import com.loitp.views.iv.scrollParallax.style.VerticalMovingStyle
import kotlinx.android.synthetic.main.activity_scroll_parallax_image_view.*
import vn.loitp.R

@LogTag("ScrollParallaxImageViewActivity")
@IsFullScreen(false)
class ScrollParallaxImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scroll_parallax_image_view
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/gjiazhe/ScrollParallaxImageView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ScrollParallaxImageViewActivity::class.java.simpleName
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
