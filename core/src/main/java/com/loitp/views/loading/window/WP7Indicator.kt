package com.loitp.views.loading.window

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import com.loitp.views.loading.window.Utils.px2dp

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@SuppressLint("ViewConstructor")
internal class WP7Indicator(
    context: Context,
    indicatorHeight: Int,
    private val color: Int,
    radius: Int
) : View(context) {

    private var objectAnimator: ObjectAnimator? = null

    init {
        initialize(indicatorHeight = indicatorHeight, radius = radius)
    }

    private fun initialize(
        indicatorHeight: Int,
        radius: Int
    ) {
        this.background = getCube(radius)
        val lp = LinearLayout.LayoutParams(
            px2dp(context = context, px = indicatorHeight),
            px2dp(context = context, px = indicatorHeight)
        )
        lp.rightMargin = px2dp(context = context, px = (1.5f * indicatorHeight).toInt())
        layoutParams = lp
        startAnim(0, 0)
        removeAnim()
    }

    private fun getCube(radius: Int): GradientDrawable {
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.RECTANGLE
        drawable.setColor(color)
        drawable.cornerRadius = px2dp(context, radius).toFloat()
        return drawable
    }

    fun startAnim(
        animationDuration: Long,
        delay: Long
    ) {
        objectAnimator = ObjectAnimator.ofFloat(this, "translationX", +1000f, -1000f)
        objectAnimator?.apply {
            interpolator = WPInterpolator()
            duration = animationDuration
            repeatMode = ValueAnimator.RESTART
            repeatCount = ValueAnimator.INFINITE
            startDelay = delay
            start()
        }
    }

    fun removeAnim() {
        objectAnimator?.apply {
            removeAllListeners()
            cancel()
            end()
        }
    }
}
