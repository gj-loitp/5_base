package com.loitp.views.layout.heart

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class HeartView : AppCompatImageView {

    companion object {
        private val sPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        private var sHeart: Bitmap? = null
        private var sHeartBorder: Bitmap? = null
        private val sCanvas = Canvas()

        private fun createBitmapSafely(width: Int, height: Int): Bitmap? {
            try {
                return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            } catch (error: OutOfMemoryError) {
                error.printStackTrace()
            }
            return null
        }
    }

    private var mHeartResId = R.drawable.l_heart_icon
    private var mHeartBorderResId = R.drawable.l_heart_border

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context) : super(context)

    fun setColor(color: Int) {
        val heart = createHeart(color)
        setImageDrawable(BitmapDrawable(resources, heart))
    }

    fun setColorAndDrawables(
        color: Int,
        heartResId: Int,
        heartBorderResId: Int
    ) {
        if (heartResId != mHeartResId) {
            sHeart = null
        }
        if (heartBorderResId != mHeartBorderResId) {
            sHeartBorder = null
        }
        mHeartResId = heartResId
        mHeartBorderResId = heartBorderResId
        setColor(color)
    }

    private fun createHeart(color: Int): Bitmap? {
        if (sHeart == null) {
            sHeart = BitmapFactory.decodeResource(resources, mHeartResId)
        }
        if (sHeartBorder == null) {
            sHeartBorder = BitmapFactory.decodeResource(resources, mHeartBorderResId)
        }
        val heart = sHeart
        val heartBorder = sHeartBorder ?: return null
        val bitmap = createBitmapSafely(heartBorder.width, heartBorder.height)
            ?: return null
        val canvas = sCanvas
        canvas.setBitmap(bitmap)
        val paint = sPaint
        canvas.drawBitmap(heartBorder, 0f, 0f, paint)
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        heart?.let {
            val dx = (heartBorder.width - it.width) / 2f
            val dy = (heartBorder.height - it.height) / 2f
            canvas.drawBitmap(it, dx, dy, paint)
        }

        paint.colorFilter = null
        canvas.setBitmap(null)
        return bitmap
    }
}
