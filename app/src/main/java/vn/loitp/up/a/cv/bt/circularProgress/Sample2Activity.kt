package vn.loitp.up.a.cv.bt.circularProgress

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import com.dd.CircularProgressButton
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.LCpbSample2Binding

@LogTag("Sample2Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class Sample2Activity : BaseActivityFont() {
    private lateinit var binding: LCpbSample2Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LCpbSample2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.circularButton1.setSafeOnClickListener {
            if (binding.circularButton1.progress == 0) {
                simulateSuccessProgress(binding.circularButton1)
            } else {
                binding.circularButton1.progress = 0
            }
        }
        binding.circularButton2.setSafeOnClickListener {
            if (binding.circularButton2.progress == 0) {
                simulateErrorProgress(binding.circularButton2)
            } else {
                binding.circularButton2.progress = 0
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