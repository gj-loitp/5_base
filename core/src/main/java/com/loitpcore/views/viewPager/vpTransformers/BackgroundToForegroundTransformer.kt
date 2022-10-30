package com.loitpcore.views.viewPager.vpTransformers

import android.view.View
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class BackgroundToForegroundTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        val height = view.height.toFloat()
        val width = view.width.toFloat()
        val scale = min(if (position < 0) 1f else abs(1f - position), 0.5f)

        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = width * 0.5f
        view.pivotY = height * 0.5f
        view.translationX = if (position < 0) width * position else -width * position * 0.25f
    }

    @Suppress("unused")
    private fun min(`val`: Float, min: Float): Float {
        return if (`val` < min) min else `val`
    }
}
