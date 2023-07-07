package vn.loitp.up.a.anim.valueAnimator

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.*
import vn.loitp.R
import vn.loitp.databinding.AAnimationValueAnimatorBinding

// https://viblo.asia/p/custom-view-trong-android-gGJ59br9KX2
@LogTag("ValueAnimatorActivity")
@IsFullScreen(false)
class ValueAnimatorActivity : BaseActivityFont() {

    private lateinit var binding: AAnimationValueAnimatorBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationValueAnimatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ValueAnimatorActivity::class.java.simpleName
        }
        binding.btStart.setSafeOnClickListener {
            startAnim()
        }
    }

    private var valueAnimator: ValueAnimator? = null

    @SuppressLint("SetTextI18n")
    private fun startAnim() {
        val min = 0
        val max = 500
        val range = max - min.toFloat()
        val duration = 2000
        valueAnimator = ValueAnimator.ofInt(min, max)
        valueAnimator?.let { va ->
            va.duration = duration.toLong()
            va.interpolator = DecelerateInterpolator()
            val spaceW = (screenWidth - binding.view.width) / range
            val spaceH =
                (screenHeight - getStatusBarHeight() - getBottomBarHeight() - binding.view.height) / range

            va.addUpdateListener { animation: ValueAnimator ->
                val value = animation.animatedValue as Int
                binding.tvDebug.text =
                    "onAnimationUpdate: " + value + " -> " + spaceW * value + " x " + spaceH * value
                updateUI(view = binding.view, posX = spaceW * value, posY = spaceH * value)
            }
            va.start()
        }
    }

    private fun updateUI(view: View, posX: Float, posY: Float) {
        view.translationX = posX
        view.translationY = posY
    }

    override fun onDestroy() {
        valueAnimator?.cancel()
        super.onDestroy()
    }
}
