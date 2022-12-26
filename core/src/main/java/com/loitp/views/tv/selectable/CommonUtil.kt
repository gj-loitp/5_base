package com.loitp.views.tv.selectable

import android.content.Context

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object CommonUtil {
    @JvmStatic

    fun dpTpPx(
        dp: Float,
        context: Context
    ): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.toInt()
    }
}
