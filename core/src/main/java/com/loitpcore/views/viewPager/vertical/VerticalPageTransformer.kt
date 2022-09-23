package com.loitpcore.views.viewPager.vertical

import android.view.View
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class VerticalPageTransformer : ViewPager.PageTransformer {
    override fun transformPage(view: View, position: Float) {

        view.width
        when {
            position < -1 -> { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            }
            position <= 0 -> { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.alpha = 1f
                // view.setTranslationX(1);
                view.scaleX = 1f
                view.scaleY = 1f
                val yPosition = position * view.height
                view.translationY = yPosition
                view.translationX = -1 * view.width * position
            }
            position <= 1 -> { // (0,1]
                // Fade the page out.
                view.alpha = 1 - position
                view.translationX = -1 * view.width * position

                // Scale the page down (between MIN_SCALE and 1)
                val minScale = 0.75f
                val scaleFactor = (minScale + (1 - minScale) * (1 - abs(position)))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }
    }
}
