package com.loitp.views.vp.parrallax

import android.animation.FloatEvaluator
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ParallaxTransformer : ViewPager.PageTransformer {
    var parallaxMode: ParallaxMode? = null
    var interpolator: Interpolator = LinearInterpolator()
    private val mEvaluator: FloatEvaluator = FloatEvaluator()
    var outset: Int = 0
    private var mOutsetFraction = 0.5f

    fun setOutsetFraction(outsetFraction: Float) {
        this.mOutsetFraction = outsetFraction
    }

    override fun transformPage(
        page: View,
        position: Float
    ) {
        page.translationX = 0f
        if (position == 0f) {
            return
        }
        when (parallaxMode) {
            ParallaxMode.LEFT_OVERLAY -> if (position > 0) {
                transform(page, position)
            } else if (position < 0) {
                bringViewToFront(page)
            }
            ParallaxMode.RIGHT_OVERLAY -> if (position < 0) {
                transform(page, position)
            } else if (position > 0) {
                bringViewToFront(page)
            }
            ParallaxMode.NONE -> {
            }
            else -> {}
        }
    }

    private fun bringViewToFront(view: View) {
        val group = view.parent as ViewGroup
        val index = group.indexOfChild(view)
        if (index != group.childCount - 1) {
            view.bringToFront()
        }
    }

    private fun transform(
        page: View,
        position: Float
    ) {
        val interpolatorPosition: Float
        var translationX: Float
        val pageWidth = page.width
        if (outset <= 0) {
            outset = (mOutsetFraction * page.width).toInt()
        }

        if (position < 0) {
            interpolatorPosition = interpolator.getInterpolation(abs(position))
            translationX = -mEvaluator.evaluate(interpolatorPosition, 0, pageWidth - outset)
        } else {
            interpolatorPosition = interpolator.getInterpolation(position)
            translationX = mEvaluator.evaluate(interpolatorPosition, 0, pageWidth - outset)
        }
        translationX += -page.width * position
        page.translationX = translationX
    }
}
