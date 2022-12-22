package com.loitp.views.shadowViewHelper

import android.graphics.* // ktlint-disable no-wildcard-imports
import android.graphics.drawable.Drawable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ShadowViewDrawable(
    private val shadowProperty: ShadowProperty,
    color: Int,
    private val rx: Float,
    private val ry: Float
) : Drawable() {
    private val paint: Paint = Paint()
    private val bounds = RectF()
    private var width: Int = 0
    private var height: Int = 0
    private val shadowOffset: Int = this.shadowProperty.shadowOffset
    private var drawRect: RectF = RectF()

    private val srcOut = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

    init {
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        paint.style = Paint.Style.FILL
        paint.color = color
        paint.setShadowLayer(
            shadowProperty.getShadowRadius().toFloat(),
            shadowProperty.getShadowDx().toFloat(), shadowProperty.getShadowDy().toFloat(),
            shadowProperty.getShadowColor()
        )
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        if (bounds.right - bounds.left > 0 && bounds.bottom - bounds.top > 0) {
            this.bounds.left = bounds.left.toFloat()
            this.bounds.right = bounds.right.toFloat()
            this.bounds.top = bounds.top.toFloat()
            this.bounds.bottom = bounds.bottom.toFloat()
            width = (this.bounds.right - this.bounds.left).toInt()
            height = (this.bounds.bottom - this.bounds.top).toInt()

            //            drawRect = new RectF(shadowOffset, shadowOffset, width - shadowOffset, height - shadowOffset);
            //            drawRect = new RectF(0, 0, width, height - shadowOffset);

            val shadowSide = shadowProperty.getShadowSide()
            val left =
                if (shadowSide and ShadowProperty.LEFT == ShadowProperty.LEFT) shadowOffset else 0
            val top =
                if (shadowSide and ShadowProperty.TOP == ShadowProperty.TOP) shadowOffset else 0
            val right =
                width - if (shadowSide and ShadowProperty.RIGHT == ShadowProperty.RIGHT) shadowOffset else 0
            val bottom =
                height - if (shadowSide and ShadowProperty.BOTTOM == ShadowProperty.BOTTOM) shadowOffset else 0

            drawRect = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())

            invalidateSelf()
        }
    }

    override fun draw(canvas: Canvas) {
        paint.xfermode = null

        canvas.drawRoundRect(
            drawRect,
            rx, ry,
            paint
        )

        paint.xfermode = srcOut
        //        paint.setColor(Color.TRANSPARENT);
        canvas.drawRoundRect(drawRect, rx, ry, paint)
    }

    fun setColor(color: Int): ShadowViewDrawable {
        paint.color = color
        return this
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun setColorFilter(cf: ColorFilter?) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.UNKNOWN
    }
}
