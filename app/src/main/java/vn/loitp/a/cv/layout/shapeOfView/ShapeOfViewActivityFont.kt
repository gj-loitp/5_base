package vn.loitp.a.cv.layout.shapeOfView

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_layout_shape_of_view.*
import vn.loitp.R

@LogTag("ShapeOfViewActivity")
@IsFullScreen(false)
class ShapeOfViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_shape_of_view
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
                        context.openUrlInBrowser(
                            url = "https://github.com/florent37/ShapeOfView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShapeOfViewActivityFont::class.java.simpleName
        }

        ValueAnimator.ofFloat(0f, 200f, 0f).apply {
            addUpdateListener { animation ->
                roundRect.bottomLeftRadius = (animation.animatedValue as Float)
            }
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }.start()

        kbv.loadGlide(
            any = Constants.URL_IMG,
        )
    }
}
