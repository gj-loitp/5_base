package com.loitpcore.views.layout.heartlayout

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.loitpcore.R
import com.loitpcore.views.layout.heartlayout.AbstractPathAnimator.Config.Companion.fromTypeArray

class LHeartLayout : RelativeLayout {
    private var abstractPathAnimator: AbstractPathAnimator? = null

    constructor(context: Context?) : super(context) {
        init(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LHeartLayout, defStyleAttr, 0)
        abstractPathAnimator = PathAnimator(fromTypeArray(typedArray))
        typedArray.recycle()
    }

    var animator: AbstractPathAnimator?
        get() = abstractPathAnimator
        set(animator) {
            clearAnimation()
            abstractPathAnimator = animator
        }

    override fun clearAnimation() {
        for (i in 0 until childCount) {
            getChildAt(i)?.clearAnimation()
        }
        removeAllViews()
    }

    fun addHeart(color: Int) {
        val heartView = HeartView(context)
        heartView.setColor(color)
        abstractPathAnimator?.start(heartView, this)
    }

    fun addHeart(color: Int, heartResId: Int, heartBorderResId: Int) {
        val heartView = HeartView(context)
        heartView.setColorAndDrawables(
            color = color,
            heartResId = heartResId,
            heartBorderResId = heartBorderResId
        )
        abstractPathAnimator?.start(heartView, this)
    }
}
