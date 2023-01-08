package com.loitp.core.ext

import android.graphics.PorterDuff
import android.widget.ProgressBar
import android.widget.SeekBar

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun ProgressBar?.setColorProgressBar(
    color: Int
) {
    this?.indeterminateDrawable?.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
}

fun ProgressBar?.setProgressBarVisibility(
    visibility: Int
) {
    this?.visibility = visibility
}

fun SeekBar?.setColorSeekBar(
    color: Int
) {
    this?.apply {
        progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        thumb.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}
