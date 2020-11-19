package com.views.textview.selectable

import android.content.Context

/**
 * Created by www.muathu@gmail.com on 12/25/2017.
 */

object CommonUtil {
    @JvmStatic

    fun dpTpPx(dp: Float, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.toInt()
    }
}
