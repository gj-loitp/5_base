package com.loitpcore.views.viewPager.viewPagerTransformers

import android.view.View

class AccordionTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        view.pivotX = (if (position < 0) 0 else view.width).toFloat()
        view.scaleX = if (position < 0) 1f + position else 1f - position
    }
}
