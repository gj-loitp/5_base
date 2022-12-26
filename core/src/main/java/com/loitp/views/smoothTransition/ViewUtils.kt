package com.loitp.views.smoothTransition

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object ViewUtils {
    var screenWidth = 0
        private set
    var screenHeight = 0
        private set

    @JvmStatic
    fun init(
        context: Context
    ) {
        val res = context.resources
        val metrics = res.displayMetrics
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
    }

    /**
     * change dip value to pixel value for certain screen size
     *
     * @param dipValue
     * @param context
     * @return
     */
    @Suppress("unused")
    fun getPixels(
        dipValue: Int,
        context: Context
    ): Int {
        val result: Int
        val res = context.resources
        result = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue.toFloat(),
            res.displayMetrics
        ).toInt()
        return result
    }

    /**
     * change View to Bitmap Object
     *
     * @param v
     * @return
     */
    @Suppress("unused")
    fun loadBitmapFromView(
        v: View?
    ): Bitmap? {
        if (v == null) {
            return null
        }
        val screenshot: Bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val c = Canvas(screenshot)
        c.translate(-v.scrollX.toFloat(), -v.scrollY.toFloat())
        v.draw(c)
        return screenshot
    }

    /**
     * compute sample size you want
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    @Suppress("unused")
    fun computeSampleSize(
        options: BitmapFactory.Options,
        minSideLength: Int,
        maxNumOfPixels: Int
    ): Int {
        val initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels)
        var roundedSize: Int
        if (initialSize <= 8) {
            roundedSize = 1
            while (roundedSize < initialSize) {
                roundedSize = roundedSize shl 1
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8
        }
        return roundedSize
    }

    /**
     * compute bitmap initial sample size
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private fun computeInitialSampleSize(
        options: BitmapFactory.Options,
        minSideLength: Int,
        maxNumOfPixels: Int
    ): Int {
        val w = options.outWidth.toDouble()
        val h = options.outHeight.toDouble()
        val lowerBound = if (maxNumOfPixels == -1) 1 else ceil(sqrt(w * h / maxNumOfPixels)).toInt()
        val upperBound = if (minSideLength == -1) 128 else min(
            floor(w / minSideLength),
            floor(h / minSideLength)
        ).toInt()
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound
        }
        return if (maxNumOfPixels == -1 && minSideLength == -1) {
            1
        } else if (minSideLength == -1) {
            lowerBound
        } else {
            upperBound
        }
    }

    fun getStatusBarHeight(context: Activity): Int {
        val rect = Rect()
        context.window.decorView.getWindowVisibleDisplayFrame(rect)
        return rect.top
    }
}
