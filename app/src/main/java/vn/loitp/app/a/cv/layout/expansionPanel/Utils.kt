package vn.loitp.app.a.cv.layout.expansionPanel

import android.content.Context
import android.util.DisplayMetrics

object Utils {
    @JvmStatic
    fun dpToPx(context: Context, dp: Float): Int {
        return (dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    @Suppress("unused")
    fun pxToDp(context: Context, px: Float): Float {
        return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
