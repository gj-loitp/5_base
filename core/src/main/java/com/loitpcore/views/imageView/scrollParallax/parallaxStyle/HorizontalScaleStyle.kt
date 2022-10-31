package com.loitpcore.views.imageView.scrollParallax.parallaxStyle

import android.graphics.Canvas
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView.ParallaxStyle

/**
 * When the imageView is scrolling horizontally, the image in imageView will be scaled.
 * The scale ratio is according to the horizontal position of the imageView and range
 * from 1.0f to `finalScaleRatio`.
 * When the imageView is at the middle of the screen, the scale ratio is 1.0f. And When
 * it just scroll out of the screen, the scale ratio is `finalScaleRatio`.
 *
 *
 * Created by gjz on 26/11/2016.
 */
class HorizontalScaleStyle : ParallaxStyle {
    private var finalScaleRatio = 0.7f

    constructor()

    @Suppress("unused")
    constructor(finalScaleRatio: Float) {
        this.finalScaleRatio = finalScaleRatio
    }

    @Suppress("unused")
    fun setFinalScaleRatio(scale: Float) {
        finalScaleRatio = scale
    }

    override fun transform(view: LScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's width and height
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        val vHeight = view.height - view.paddingTop - view.paddingBottom
        // device's width
        val dWidth = view.resources.displayMetrics.widthPixels
        if (vWidth >= dWidth) {
            // Do nothing if imageView's width is bigger than device's width.
            return
        }
        val scale: Float
        val pivot = (dWidth - vWidth) / 2
        scale = if (x <= pivot) {
            2 * (1 - finalScaleRatio) * (x + vWidth) / (dWidth + vWidth) + finalScaleRatio
        } else {
            2 * (1 - finalScaleRatio) * (dWidth - x) / (dWidth + vWidth) + finalScaleRatio
        }
        canvas.scale(scale, scale, (vWidth / 2).toFloat(), (vHeight / 2).toFloat())
    }

    override fun onAttachedToImageView(view: LScrollParallaxImageView) {}

    override fun onDetachedFromImageView(view: LScrollParallaxImageView) {}
}
