package com.loitpcore.views.viewPager.vpTransformers

import android.view.View

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FlipVerticalTransformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        val rotation = -180f * position

        view.visibility = if (rotation > 90f || rotation < -90f) View.INVISIBLE else View.VISIBLE
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.rotationX = rotation
    }
}
