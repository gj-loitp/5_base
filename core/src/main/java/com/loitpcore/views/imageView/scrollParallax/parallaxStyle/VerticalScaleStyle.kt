package com.loitpcore.views.imageView.scrollParallax.parallaxStyle

import android.graphics.Canvas
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView.ParallaxStyle

/**
 * When the imageView is scrolling vertically, the image in imageView will be scaled.
 * The scale ratio is according to the vertical position of the imageView and range
 * from 1.0f to `finalScaleRatio`.
 * When the imageView is at the middle of the screen, the scale ratio is 1.0f. And When
 * it just scroll out of the screen, the scale ratio is `finalScaleRatio`.
 *
 *
 * Created by gjz on 25/11/2016.
 */
@Suppress("unused")
class VerticalScaleStyle : ParallaxStyle {
    private var finalScaleRatio = 0.7f

    constructor()
    constructor(finalScaleRatio: Float) {
        this.finalScaleRatio = finalScaleRatio
    }

    fun setFinalScaleRatio(scale: Float) {
        finalScaleRatio = scale
    }

    override fun transform(view: LScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's width and height
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        val vHeight = view.height - view.paddingTop - view.paddingBottom
        // device's height
        val dHeight = view.resources.displayMetrics.heightPixels
        if (vHeight >= dHeight) {
            // Do nothing if imageView's height is bigger than device's height.
            return
        }
        val scale: Float
        val pivot = (dHeight - vHeight) / 2
        scale = if (y <= pivot) {
            2 * (1 - finalScaleRatio) * (y + vHeight) / (dHeight + vHeight) + finalScaleRatio
        } else {
            2 * (1 - finalScaleRatio) * (dHeight - y) / (dHeight + vHeight) + finalScaleRatio
        }
        canvas.scale(scale, scale, (vWidth / 2).toFloat(), (vHeight / 2).toFloat())
    }

    override fun onAttachedToImageView(view: LScrollParallaxImageView) {}

    override fun onDetachedFromImageView(view: LScrollParallaxImageView) {}
}
