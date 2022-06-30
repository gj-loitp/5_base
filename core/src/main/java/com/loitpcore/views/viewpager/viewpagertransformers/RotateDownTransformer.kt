package com.loitpcore.views.viewpager.viewpagertransformers

import android.view.View

class RotateDownTransformer : BaseTransformer() {

    override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(view: View, position: Float) {
        val width = view.width.toFloat()
        val height = view.height.toFloat()
        val rotation = ROT_MOD * position * -1.25f

        view.pivotX = width * 0.5f
        view.pivotY = height
        view.rotation = rotation
    }

    companion object {
        private const val ROT_MOD = -15f
    }
}
