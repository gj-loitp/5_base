package com.loitp.anim.flySchool

import android.content.res.Resources
import android.graphics.PorterDuff
import android.widget.ImageView
import com.loitp.core.ext.getRandomNumber
import com.loitp.core.utilities.LStoreUtil.Companion.randomColor

/**
 * Utility class with simple utility functions
 */
object Utils {
    /**
     * @param dp : Dimension in dp
     * Calculates and returns the dimension value in pixels from dp
     */
    @JvmStatic
    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    }

    /**
     * Checks and tell us whether the android phone is on version < LOLLIPOP or not
     */
//    val isLowerThanLollipop: Boolean
//        get() = false

    @JvmStatic
    fun setHeart(imageView: ImageView) {
        val size = 150.getRandomNumber() + 80
        imageView.layoutParams.height = size
        imageView.layoutParams.width = size
        imageView.requestLayout()

        val color = randomColor
        imageView.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }
}
