package com.views.textview.selectable

import android.content.Context

object CommonUtil {
    @JvmStatic

    fun dpTpPx(dp: Float, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.toInt()
    }
}
