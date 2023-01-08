package com.loitp.core.ext

import android.graphics.drawable.GradientDrawable

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun createGradientDrawableWithRandomColor(): GradientDrawable {
    val color = getRandomColor()
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(color)
    gradientDrawable.cornerRadius = 0f
    gradientDrawable.setStroke(1, color)
    return gradientDrawable
}


fun createGradientDrawableWithColor(
    colorMain: Int,
    colorStroke: Int
): GradientDrawable {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(colorMain)
    gradientDrawable.cornerRadius = 90f
    gradientDrawable.setStroke(3, colorStroke)
    return gradientDrawable
}

