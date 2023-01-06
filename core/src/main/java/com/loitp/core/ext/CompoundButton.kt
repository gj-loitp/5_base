package com.loitp.core.ext

import android.content.res.ColorStateList
import android.widget.CompoundButton

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun CompoundButton.setButtonTintList(
    color: Int
) {
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_enabled)
        ), intArrayOf(
            color,  // disabled
            color // enabled
        )
    )
    this.buttonTintList = colorStateList
}
