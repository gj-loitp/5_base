package com.loitp.views.vp.vpTransformers

import android.view.View
import kotlin.math.abs

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DepthPageTransformer : BaseTransformer() {

    override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(
        view: View,
        position: Float
    ) {
        if (position <= 0f) {
            view.translationX = 0f
            view.scaleX = 1f
            view.scaleY = 1f
        } else if (position <= 1f) {
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - abs(position))
            view.alpha = 1 - position
            view.pivotY = 0.5f * view.height
            view.translationX = view.width * -position
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
        }
    }

    companion object {
        private const val MIN_SCALE = 0.75f
    }
}
