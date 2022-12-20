package com.loitp.animation.morphTransitions

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.Property
import androidx.annotation.ColorInt

internal class MorphDrawable(
    @ColorInt color: Int,
    private var cornerRadius: Float
) : Drawable() {

    companion object {
        val CORNER_RADIUS: Property<MorphDrawable, Float> =
            object : FloatProperty<MorphDrawable>("cornerRadius") {
                override fun setValue(obj: MorphDrawable, value: Float) {
                    obj.setCornerRadius(value)
                }

                override operator fun get(morphDrawable: MorphDrawable): Float {
                    return morphDrawable.getCornerRadius()
                }
            }
        val COLOR: Property<MorphDrawable, Int> = object : IntProperty<MorphDrawable>("color") {
            override fun setValue(obj: MorphDrawable, value: Int) {
                obj.color = value
            }

            override operator fun get(morphDrawable: MorphDrawable): Int {
                return morphDrawable.color
            }
        }
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = color
    }

    fun getCornerRadius(): Float {
        return cornerRadius
    }

    fun setCornerRadius(cornerRadius: Float) {
        this.cornerRadius = cornerRadius
        invalidateSelf()
    }

    var color: Int
        get() = paint.color
        set(color) {
            paint.color = color
            invalidateSelf()
        }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(
            bounds.left.toFloat(),
            bounds.top.toFloat(),
            bounds.right.toFloat(),
            bounds.bottom.toFloat(),
            cornerRadius,
            cornerRadius,
            paint
        )
    }

    override fun getOutline(outline: Outline) {
        outline.setRoundRect(bounds, cornerRadius)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
        invalidateSelf()
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return paint.alpha
    }
}
