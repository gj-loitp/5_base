package vn.loitp.app.a.cv.layout.greedo

import android.content.Context
import android.util.TypedValue

object MeasUtils {
    @Suppress("unused")
    fun pxToDp(
        px: Int,
        context: Context
    ): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PX, px.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    fun dpToPx(
        dp: Float,
        context: Context
    ): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            context.resources.displayMetrics
        ).toInt()
    }
}
