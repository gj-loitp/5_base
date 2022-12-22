package com.loitp.views.iv.zoom

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import androidx.annotation.IntRange
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ColorGridDrawable : Drawable() {

    companion object {
        private const val ROWS = 20
        private const val COLS = 20
        private val R = Random()
        private val COLOR_CACHE = Array(ROWS) { IntArray(COLS) }
        private fun getColor(row: Int, col: Int): Int {
            if (COLOR_CACHE[row][col] == 0) {
                val r = 140 + R.nextInt(100)
                val g = 140 + R.nextInt(100)
                val b = 50 + R.nextInt(100)
                COLOR_CACHE[row][col] = Color.rgb(r, g, b)
            }
            return COLOR_CACHE[row][col]
        }
    }

    private val mPaint = Paint()
    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
    private val mRect = Rect(0, 0, 150, 150)

    init {
        mTextPaint.color = Color.WHITE
        mTextPaint.textAlign = Paint.Align.CENTER
        mTextPaint.textSize = intrinsicHeight.toFloat() / 10f
    }

    override fun setAlpha(@IntRange(from = 0, to = 255) i: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun getIntrinsicHeight(): Int {
        return mRect.height() * COLS
    }

    override fun getIntrinsicWidth(): Int {
        return mRect.width() * ROWS
    }

    override fun draw(canvas: Canvas) {
        for (col in 0 until COLS) {
            for (row in 0 until ROWS) {
                val restore = canvas.save()
                canvas.translate(150 * col.toFloat(), 150 * row.toFloat())
                mPaint.color = getColor(row, col)
                canvas.drawRect(mRect, mPaint)
                canvas.restoreToCount(restore)
            }
        }
        canvas.drawText(
            "This is a drawable.",
            intrinsicWidth / 2f,
            intrinsicHeight / 2f,
            mTextPaint
        )
    }
}
