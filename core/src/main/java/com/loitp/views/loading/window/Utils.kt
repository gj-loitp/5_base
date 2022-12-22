package com.loitp.views.loading.window

import android.content.Context

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
object Utils {
    @JvmStatic
    fun px2dp(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px * scale + 0.5f).toInt()
    }
}
