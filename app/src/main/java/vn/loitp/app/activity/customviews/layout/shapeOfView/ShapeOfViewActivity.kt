package vn.loitp.app.activity.customviews.layout.shapeOfView

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_shape_of_view_layout.*
import vn.loitp.app.R

@LogTag("ShapeOfViewActivity")
@IsFullScreen(false)
class ShapeOfViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shape_of_view_layout
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
                            url = "https://github.com/florent37/ShapeOfView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
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
