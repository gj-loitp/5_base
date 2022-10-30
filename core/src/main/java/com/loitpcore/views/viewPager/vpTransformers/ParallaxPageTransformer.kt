package com.loitpcore.views.viewPager.vpTransformers

import android.view.View

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Suppress("unused")
class ParallaxPageTransformer(private val viewToParallax: Int) : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        val pageWidth = view.width
        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 1f
            position <= 1 -> // [-1,1]
                view.findViewById<View>(viewToParallax).translationX =
                    -position * (pageWidth / 2) // Half the normal speed
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 1f
        }
    }
}
