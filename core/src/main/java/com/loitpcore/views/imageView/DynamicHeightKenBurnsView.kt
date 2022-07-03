package com.loitpcore.views.imageView

import android.content.Context
import android.util.AttributeSet
import com.flaviofaria.kenburnsview.KenBurnsView

class DynamicHeightKenBurnsView : KenBurnsView {
    private var mAspectRatio = 1.5f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    fun setAspectRatio(aspectRatio: Float) {

        mAspectRatio = aspectRatio
        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val measuredWidth = measuredWidth
        setMeasuredDimension(measuredWidth, (measuredWidth / mAspectRatio).toInt())
    }
}
