package com.loitpcore.views.viewPager.vpTransformers

import android.view.View
import kotlin.math.abs
import kotlin.math.max

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ZoomOutSlideTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        if (position >= -1 || position <= 1) {
            // Modify the default slide transition to shrink the page as well
            val height = view.height.toFloat()
            val scaleFactor = max(MIN_SCALE, 1 - abs(position))
            val vertMargin = height * (1 - scaleFactor) / 2
            val horzMargin = view.width * (1 - scaleFactor) / 2

            // Center vertically
            view.pivotY = 0.5f * height

            if (position < 0) {
                view.translationX = horzMargin - vertMargin / 2
            } else {
                view.translationX = -horzMargin + vertMargin / 2
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor

            // Fade the page relative to its size.
            view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        }
    }

    companion object {
        private const val MIN_SCALE = 0.85f
        private const val MIN_ALPHA = 0.5f
    }
}
