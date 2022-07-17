package com.loitpcore.views.viewPager.viewPagerTransformers

import android.view.View

class DefaultTransformer : BaseTransformer() {

    public override val isPagingEnabled: Boolean
        get() = true

    override fun onTransform(view: View, position: Float) {}
}
