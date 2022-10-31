package com.loitpcore.views.viewPager.vpTransformers

import android.view.View

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class StackTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        view.translationX = if (position < 0) 0f else -view.width * position
    }
}
