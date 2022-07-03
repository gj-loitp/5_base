package com.loitpcore.views.viewPager.viewPagerTransformers

import android.view.View
import kotlin.math.abs

class ZoomOutTranformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        val scale = 1f + abs(position)
        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
        if (position == -1f) {
            view.translationX = (view.width * -1).toFloat()
        }
    }
}
