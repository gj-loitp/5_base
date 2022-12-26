package com.loitp.views.iv.scrollParallax.style

import android.graphics.Canvas
import android.widget.ImageView
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView
import com.loitp.views.iv.scrollParallax.LScrollParallaxImageView.ParallaxStyle
import kotlin.math.abs

/**
 * When the imageView is scrolling vertically, the image in imageView will be
 * also scrolling vertically if the image' height is bigger than imageView's height.
 *
 *
 * The image will not over scroll to it's view bounds.
 *
 *
 * Note: it only supports imageView with CENTER_CROP scale type.
 *
 *
 * Created by gjz on 25/11/2016.
 */
class VerticalMovingStyle : ParallaxStyle {

    override fun onAttachedToImageView(view: LScrollParallaxImageView) {
        // only supports CENTER_CROP
        view.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun onDetachedFromImageView(view: LScrollParallaxImageView) {}

    @Suppress("NAME_SHADOWING")
    override fun transform(
        view: LScrollParallaxImageView,
        canvas: Canvas,
        x: Int,
        y: Int
    ) {
        var y = y
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

        // device's height
        val dHeight = view.resources.displayMetrics.heightPixels
        if (iWidth * vHeight < iHeight * vWidth) {
            // avoid over scroll
            if (y < -vHeight) {
                y = -vHeight
            } else if (y > dHeight) {
                y = dHeight
            }
            val imgScale = vWidth.toFloat() / iWidth.toFloat()
            val maxDy = abs((iHeight * imgScale - vHeight) * 0.5f)
            val translateY = -(2 * maxDy * y + maxDy * (vHeight - dHeight)) / (vHeight + dHeight)
            canvas.translate(0f, translateY)
        }
    }
}
