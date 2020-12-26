package com.views.progressloadingview.window

import android.view.animation.Interpolator
import kotlin.math.pow

class WPInterpolator : Interpolator {

    override fun getInterpolation(v: Float): Float {
        return if (v > 0.3f && v < 0.70f) {
            (-(v - 0.5) / 6 + 0.5f).toFloat()
        } else {
            (-4 * (v - 0.5).pow(3.0) + 0.5).toFloat()
        }
    }
}
