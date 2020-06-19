package vn.loitp.app.activity.customviews.layout.shapeofview

import android.animation.ValueAnimator
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import com.core.utilities.LSocialUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_layout_shape_of_view.*
import vn.loitp.app.R

//https://github.com/florent37/ShapeOfView
class ShapeOfViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvMenu.setSafeOnClickListener {
            LSocialUtil.openUrlInBrowser(context = activity, url = "https://github.com/florent37/ShapeOfView")
        }

        ValueAnimator.ofFloat(0f, 200f, 0f).apply {
            addUpdateListener { animation -> roundRect.bottomLeftRadius = (animation.animatedValue as Float) }
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }.start()

        LImageUtil.load(context = activity, url = Constants.URL_IMG, imageView = kbv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_shape_of_view
    }
}
