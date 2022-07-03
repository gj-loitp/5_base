package com.loitpcore.views.viewPager.viewPagerTransformers

import android.view.View

class RotateUpTransformer : BaseTransformer() {

    override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(view: View, position: Float) {
        val width = view.width.toFloat()
        val rotation = ROT_MOD * position

        view.pivotX = width * 0.5f
        view.pivotY = 0f
        view.translationX = 0f
        view.rotation = rotation
    }

    companion object {
        private const val ROT_MOD = -15f
    }
}
