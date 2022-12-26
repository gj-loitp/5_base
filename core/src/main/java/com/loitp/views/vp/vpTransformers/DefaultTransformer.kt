package com.loitp.views.vp.vpTransformers

import android.view.View

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DefaultTransformer : BaseTransformer() {

    public override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(
        view: View,
        position: Float
    ) {}
}
