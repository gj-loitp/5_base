package com.loitp.views.iv.scrollParallax.style

import android.graphics.Canvas
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView.ParallaxStyle

/**
 * When the imageView is scrolling vertically, the image in imageView will change its alpha.
 * The alpha is according to the vertical position of the imageView and range
 * from 1.0f to `finalAlpha`.
 * When the imageView is at the middle of the screen, the alpha is 1.0f. And When
 * it just scroll out of the screen, the alpha is `finalAlpha`.
 *
 *
 * Created by gjz on 25/11/2016.
 */
@Suppress("unused")
class VerticalAlphaStyle : ParallaxStyle {
    private var finalAlpha = 0.3f

    constructor()
    constructor(finalAlpha: Float) {
        require(!(finalAlpha < 0 || finalAlpha > 1.0f)) { "the alpha must between 0 and 1." }
        this.finalAlpha = finalAlpha
    }

    fun setFinalAlpha(alpha: Float) {
        finalAlpha = alpha
    }

    override fun transform(
        view: LScrollParallaxImageView,
        canvas: Canvas,
        x: Int,
        y: Int
    ) {
        // view's height
        val vHeight = view.height - view.paddingTop - view.paddingBottom
        // device's height
        val dHeight = view.resources.displayMetrics.heightPixels
        if (vHeight >= dHeight) {
            // Do nothing if imageView's height is bigger than device's height.
            return
        }
        val alpha: Float
        val pivot = (dHeight - vHeight) / 2
        alpha = if (y <= pivot) {
            2 * (1 - finalAlpha) * (y + vHeight) / (dHeight + vHeight) + finalAlpha
        } else {
            2 * (1 - finalAlpha) * (dHeight - y) / (dHeight + vHeight) + finalAlpha
        }
        view.alpha = alpha
    }

    override fun onAttachedToImageView(view: LScrollParallaxImageView) {}

    override fun onDetachedFromImageView(view: LScrollParallaxImageView) {}
}
