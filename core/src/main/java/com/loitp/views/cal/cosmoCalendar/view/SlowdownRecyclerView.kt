package com.loitp.views.cal.cosmoCalendar.view

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.pow

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SlowdownRecyclerView : RecyclerView {
    companion object {
        // Change pow to control speed.
        // Bigger = faster. RecyclerView default is 5.
        private const val POW = 2
    }

    private var interpolator: Interpolator? = null

    constructor(context: Context) : super(context) {
        createInterpolator()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        createInterpolator()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        createInterpolator()
    }

    private fun createInterpolator() {
        interpolator = Interpolator { t ->
            @Suppress("NAME_SHADOWING")
            var t = t
            t = abs(t - 1.0f)
            (1.0f - t.toDouble().pow(POW)).toFloat()
        }
    }

    override fun smoothScrollBy(
        dx: Int,
        dy: Int
    ) {
        super.smoothScrollBy(dx, dy, interpolator)
    }
}
