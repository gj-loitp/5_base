package com.loitpcore.views.imageView.scrollParallax.parallaxStyle

import android.graphics.Canvas
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView.ParallaxStyle

/**
 * When the imageView is scrolling horizontally, the image in imageView will change its alpha.
 * The alpha is according to the horizontal position of the imageView and range
 * from 1.0f to `finalAlpha`.
 * When the imageView is at the middle of the screen, the alpha is 1.0f. And When
 * it just scroll out of the screen, the alpha is `finalAlpha`.
 *
 *
 * Created by gjz on 26/11/2016.
 */
@Suppress("unused")
class HorizontalAlphaStyle : ParallaxStyle {
    private var finalAlpha = 0.3f

    constructor()
    constructor(finalAlpha: Float) {
        require(!(finalAlpha < 0 || finalAlpha > 1.0f)) { "the alpha must between 0 and 1." }
        this.finalAlpha = finalAlpha
    }

    fun setFinalAlpha(alpha: Float) {
        finalAlpha = alpha
    }

    override fun transform(view: LScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        // view's width
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        // device's width
        val dWidth = view.resources.displayMetrics.widthPixels
        if (vWidth >= dWidth) {
            // Do nothing if imageView's width is bigger than device's width.
            return
        }
        val alpha: Float
        val pivot = (dWidth - vWidth) / 2
        alpha = if (x <= pivot) {
            2 * (1 - finalAlpha) * (x + vWidth) / (dWidth + vWidth) + finalAlpha
        } else {
            2 * (1 - finalAlpha) * (dWidth - x) / (dWidth + vWidth) + finalAlpha
        }
        view.alpha = alpha
    }

    override fun onAttachedToImageView(view: LScrollParallaxImageView) {}

    override fun onDetachedFromImageView(view: LScrollParallaxImageView) {}
}
