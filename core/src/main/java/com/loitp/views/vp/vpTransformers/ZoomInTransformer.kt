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
class ZoomInTransformer : BaseTransformer() {

    override fun onTransform(
        view: View,
        position: Float
    ) {
        val scale = if (position < 0) position + 1f else abs(1f - position)
        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
    }
}
