package com.loitp.core.ext

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.loitp.R

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun SwipeRefreshLayout.setColorForSwipeRefreshLayout() {
    this.setColorSchemeResources(
        R.color.colorPrimary,
        R.color.vip1,
        R.color.vip2,
        R.color.vip3,
        R.color.vip4,
        R.color.vip5
    )
}

fun SwipeRefreshLayout.setProgressViewOffset(
    topMargin: Int
) {
    this.setProgressViewOffset(false, 0, topMargin)
}
