package com.views.viewpager.parrallaxviewpager

import android.animation.FloatEvaluator
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator

import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class ParallaxTransformer : ViewPager.PageTransformer {
    var mode: Mode? = null
    var interpolator: Interpolator = LinearInterpolator()
    private val mEvaluator: FloatEvaluator = FloatEvaluator()
    var outset: Int = 0
    private var mOutsetFraction = 0.5f

    fun setOutsetFraction(outsetFraction: Float) {
        this.mOutsetFraction = outsetFraction
    }

    override fun transformPage(page: View, position: Float) {
        page.translationX = 0f
        if (position == 0f) {
            return
        }
        when (mode) {
            Mode.LEFT_OVERLAY -> if (position > 0) {
                transform(page, position)
            } else if (position < 0) {
                bringViewToFront(page)
            }
            Mode.RIGHT_OVERLAY -> if (position < 0) {
                transform(page, position)
            } else if (position > 0) {
                bringViewToFront(page)
            }
            Mode.NONE -> {
            }
        }
    }

    private fun bringViewToFront(view: View) {
        val group = view.parent as ViewGroup
        val index = group.indexOfChild(view)
        if (index != group.childCount - 1) {
            view.bringToFront()
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                view.requestLayout()
                group.invalidate()
            }
        }
    }

    private fun transform(page: View, position: Float) {
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
            translationX = mEvaluator.evaluate(interpolatorPosition, 0, pageWidth - outset)!!
        }
        translationX += -page.width * position
        page.translationX = translationX
    }

}