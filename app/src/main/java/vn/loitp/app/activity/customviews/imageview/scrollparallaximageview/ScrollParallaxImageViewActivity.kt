package vn.loitp.app.activity.customviews.imageview.scrollparallaximageview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.imageview.scrollparallax.LScrollParallaxImageView
import com.views.imageview.scrollparallax.parallaxstyle.HorizontalScaleStyle
import com.views.imageview.scrollparallax.parallaxstyle.VerticalMovingStyle
import kotlinx.android.synthetic.main.activity_imageview_scrollparallax.*
import vn.loitp.app.R

// https://github.com/gjiazhe/ScrollParallaxImageView

@LogTag("ScrollParallaxImageViewActivity")
@IsFullScreen(false)
class ScrollParallaxImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_scrollparallax
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
