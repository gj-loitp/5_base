package com.loitpcore.views.viewPager.vpTransformers

import android.view.View

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AccordionTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        view.pivotX = (if (position < 0) 0 else view.width).toFloat()
        view.scaleX = if (position < 0) 1f + position else 1f - position
    }
}
