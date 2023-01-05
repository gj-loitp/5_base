package vn.loitp.a.cv.bt.circularProgress

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import com.dd.CircularProgressButton
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.l_cpb_sample_2.*
import vn.loitp.R

@LogTag("Sample2Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample2ActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_cpb_sample_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        circularButton1.setSafeOnClickListener {
            if (circularButton1.progress == 0) {
                simulateSuccessProgress(circularButton1)
            } else {
                circularButton1.progress = 0
            }
        }
        circularButton2.setSafeOnClickListener {
            if (circularButton2.progress == 0) {
                simulateErrorProgress(circularButton2)
            } else {
                circularButton2.progress = 0
            }
        }
    }

    private fun simulateSuccessProgress(button: CircularProgressButton) {
        val widthAnimation = ValueAnimator.ofInt(1, 100)
        widthAnimation.duration = 1500
        widthAnimation.interpolator = AccelerateDecelerateInterpolator()
        widthAnimation.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            button.progress = value
        }
        widthAnimation.start()
    }

    private fun simulateErrorProgress(button: CircularProgressButton) {
        val widthAnimation = ValueAnimator.ofInt(1, 99)
        widthAnimation.duration = 1500
        widthAnimation.interpolator = AccelerateDecelerateInterpolator()
        widthAnimation.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            button.progress = value
            if (value == 99) {
                button.progress = -1
            }
        }
        widthAnimation.start()
    }
}