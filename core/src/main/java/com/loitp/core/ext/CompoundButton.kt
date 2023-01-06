package com.loitp.core.ext

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.CompoundButton
import androidx.appcompat.widget.AppCompatCheckBox

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun CompoundButton.setButtonTintListColor(
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

@SuppressLint("RestrictedApi")
fun AppCompatCheckBox.setCheckBoxColor(
    uncheckedColor: Int,
    checkedColor: Int
) {
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked), // unchecked
            intArrayOf(android.R.attr.state_checked) // checked
        ),
        intArrayOf(uncheckedColor, checkedColor)
    )
    this.supportButtonTintList = colorStateList
}
