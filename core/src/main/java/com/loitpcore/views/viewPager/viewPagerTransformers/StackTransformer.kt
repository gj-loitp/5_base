package com.loitpcore.views.viewPager.viewPagerTransformers

import android.view.View

class StackTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        view.translationX = if (position < 0) 0f else -view.width * position
    }
}
