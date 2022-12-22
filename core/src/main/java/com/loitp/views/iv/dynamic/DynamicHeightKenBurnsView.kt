package com.loitp.views.iv.dynamic

import android.content.Context
import android.util.AttributeSet
import com.flaviofaria.kenburnsview.KenBurnsView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DynamicHeightKenBurnsView : KenBurnsView {
    private var mAspectRatio = 1.5f

    constructor(context: Context?) : super(context)
    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    )

    @Suppress("unused")
    fun setAspectRatio(aspectRatio: Float) {
        mAspectRatio = aspectRatio
        requestLayout()
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val measuredWidth = measuredWidth
        setMeasuredDimension(measuredWidth, (measuredWidth / mAspectRatio).toInt())
    }
}
