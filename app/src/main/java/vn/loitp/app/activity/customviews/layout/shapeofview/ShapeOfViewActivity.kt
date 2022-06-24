package vn.loitp.app.activity.customviews.layout.shapeofview

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_layout_shape_of_view.*
import vn.loitp.app.R

@LogTag("ShapeOfViewActivity")
@IsFullScreen(false)
class ShapeOfViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_shape_of_view
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/florent37/ShapeOfView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ShapeOfViewActivity::class.java.simpleName
        }

        ValueAnimator.ofFloat(0f, 200f, 0f).apply {
            addUpdateListener { animation ->
                roundRect.bottomLeftRadius = (animation.animatedValue as Float)
            }
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }.start()

        LImageUtil.load(context = this, any = Constants.URL_IMG, imageView = kbv)
    }
}
