package com.loitpcore.views.imageView.scrollParallax.parallaxStyle

import android.graphics.Canvas
import android.widget.ImageView
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView
import com.loitpcore.views.imageView.scrollParallax.LScrollParallaxImageView.ParallaxStyle
import kotlin.math.abs

/**
 * When the imageView is scrolling horizontally, the image in imageView will be
 * also scrolling horizontally if the image' width is bigger than imageView's width.
 *
 *
 * The image will not over scroll to it's view bounds.
 *
 *
 * Note: it only supports imageView with CENTER_CROP scale type.
 *
 *
 * Created by gjz on 26/11/2016.
 */
@Suppress("unused")
class HorizontalMovingStyle : ParallaxStyle {

    override fun onAttachedToImageView(view: LScrollParallaxImageView) {
        // only supports CENTER_CROP
        view.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun onDetachedFromImageView(view: LScrollParallaxImageView) {}

    @Suppress("NAME_SHADOWING")
    override fun transform(view: LScrollParallaxImageView, canvas: Canvas, x: Int, y: Int) {
        var x = x
        if (view.scaleType != ImageView.ScaleType.CENTER_CROP) {
            return
        }

        // image's width and height
        val iWidth = view.drawable.intrinsicWidth
        val iHeight = view.drawable.intrinsicHeight
        if (iWidth <= 0 || iHeight <= 0) {
            return
        }

        // view's width and height
        val vWidth = view.width - view.paddingLeft - view.paddingRight
        val vHeight = view.height - view.paddingTop - view.paddingBottom

        // device's width
        val dWidth = view.resources.displayMetrics.widthPixels
        if (iWidth * vHeight > iHeight * vWidth) {
            // avoid over scroll
            if (x < -vWidth) {
                x = -vWidth
            } else if (x > dWidth) {
                x = dWidth
            }
            val imgScale = vHeight.toFloat() / iHeight.toFloat()
            val maxDx = abs((iWidth * imgScale - vWidth) * 0.5f)
            val translateX = -(2 * maxDx * x + maxDx * (vWidth - dWidth)) / (vWidth + dWidth)
            canvas.translate(translateX, 0f)
        }
    }
}
