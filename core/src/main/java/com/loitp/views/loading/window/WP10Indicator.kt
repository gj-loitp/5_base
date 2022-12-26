package com.loitp.views.loading.window

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.widget.RelativeLayout
import com.loitp.views.loading.window.Utils.px2dp

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@SuppressLint("ViewConstructor")
class WP10Indicator(
    context: Context,
    indicatorHeight: Int,
    color: Int,
    radius: Int,
    number: Int
) : RelativeLayout(context) {

    private var objectAnimator: ObjectAnimator? = null
    private var number = 0

    init {
        initialize(
            indicatorHeight = indicatorHeight,
            color = color,
            radius = radius,
            number = number
        )
    }

    private fun initialize(
        indicatorHeight: Int,
        color: Int,
        radius: Int,
        number: Int
    ) {
        this.number = number
        val base10Indicator = Base10Indicator(context, indicatorHeight, color, radius)
        val lp = LayoutParams(
            px2dp(context = context, px = indicatorHeight * 8),
            px2dp(context = context, px = indicatorHeight * 8)
        )
        layoutParams = lp
        this.gravity = Gravity.CENTER_VERTICAL or Gravity.END
        this.addView(base10Indicator)
        startAnim(animationDuration = 0, delay = 0)
        removeAnim()
        this.alpha = 0f
    }

    fun startAnim(
        animationDuration: Long,
        delay: Long
    ) {
        objectAnimator = ObjectAnimator.ofFloat(
            this,
            "rotation",
            (number * 15).toFloat(),
            (-360 + number * 15).toFloat()
        )
        objectAnimator?.apply {
            interpolator = WPInterpolator()
            duration = animationDuration
            repeatMode = ValueAnimator.RESTART
            repeatCount = 2
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    this@WP10Indicator.alpha = 1f
                    startAlphaAnimation(animationDuration)
                }

                override fun onAnimationEnd(animator: Animator) {
                    removeAnim()
                    startAnim(animationDuration, 0)
                }

                override fun onAnimationCancel(animator: Animator) {}
                override fun onAnimationRepeat(animator: Animator) {}
            })
            startDelay = delay
            start()
        }
    }

    fun startAlphaAnimation(animationDuration: Long) {
        animate().alpha(0f).setDuration(animationDuration / 12).startDelay = 2 * animationDuration
    }

    fun removeAnim() {
        animate().alpha(0f).setDuration(0).startDelay = 0
        objectAnimator?.apply {
            removeAllListeners()
            cancel()
            end()
        }
    }
}
