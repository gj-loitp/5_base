package com.loitp.views.layout.heart

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.loitp.R
import com.loitp.views.layout.heart.AbstractPathAnimator.Config.Companion.fromTypeArray

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LHeartLayout : RelativeLayout {
    private var abstractPathAnimator: AbstractPathAnimator? = null

    constructor(context: Context?) : super(context) {
        init(null, 0)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
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

    @Suppress("unused")
    fun addHeart(
        color: Int,
        heartResId: Int,
        heartBorderResId: Int
    ) {
        val heartView = HeartView(context)
        heartView.setColorAndDrawables(
            color = color,
            heartResId = heartResId,
            heartBorderResId = heartBorderResId
        )
        abstractPathAnimator?.start(heartView, this)
    }
}
