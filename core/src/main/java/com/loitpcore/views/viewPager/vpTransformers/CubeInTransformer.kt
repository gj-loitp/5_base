package com.loitpcore.views.viewPager.vpTransformers

import android.view.View

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class CubeInTransformer : BaseTransformer() {

    public override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(view: View, position: Float) {
        // Rotate the fragment on the left or right edge
        view.pivotX = (if (position > 0) 0 else view.width).toFloat()
        view.pivotY = 0f
        view.rotationY = -90f * position
    }
}
