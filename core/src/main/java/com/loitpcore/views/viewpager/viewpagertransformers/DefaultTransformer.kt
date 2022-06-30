package com.loitpcore.views.viewpager.viewpagertransformers

import android.view.View

class DefaultTransformer : BaseTransformer() {

    public override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(view: View, position: Float) {}
}
