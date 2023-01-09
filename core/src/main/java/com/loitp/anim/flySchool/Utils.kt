package com.loitp.anim.flySchool

import android.content.res.Resources
import android.graphics.PorterDuff
import android.widget.ImageView
import com.loitp.core.ext.getRandomColor
import com.loitp.core.ext.getRandomNumber

/**
 * Utility class with simple utility functions
 */
/**
 * @param dp : Dimension in dp
 * Calculates and returns the dimension value in pixels from dp
 */
fun Int.dpToPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}

fun ImageView.setHeart() {
    val size = 150.getRandomNumber() + 80
    this.layoutParams.height = size
    this.layoutParams.width = size
    this.requestLayout()

    val color = getRandomColor()
    this.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
}

