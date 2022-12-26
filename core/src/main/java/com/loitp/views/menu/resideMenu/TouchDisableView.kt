package com.loitp.views.menu.resideMenu

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
internal class TouchDisableView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    private var mContent: View? = null
    private var isTouchDisabled = false

    var content: View?
        get() = mContent
        set(v) {
            if (mContent != null) {
                removeView(mContent)
            }
            mContent = v
            addView(mContent)
        }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        val width = getDefaultSize(0, widthMeasureSpec)
        val height = getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)
        val contentWidth = getChildMeasureSpec(widthMeasureSpec, 0, width)
        val contentHeight = getChildMeasureSpec(heightMeasureSpec, 0, height)
        mContent?.measure(contentWidth, contentHeight)
    }

    override fun onLayout(
        changed: Boolean,
        l: Int,
        t: Int,
        r: Int,
        b: Int
    ) {
        val width = r - l
        val height = b - t
        mContent?.layout(0, 0, width, height)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isTouchDisabled
    }

    fun setTouchDisable(disableTouch: Boolean) {
        isTouchDisabled = disableTouch
    }
}
