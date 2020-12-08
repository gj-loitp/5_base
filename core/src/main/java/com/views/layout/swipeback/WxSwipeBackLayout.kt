package com.views.layout.swipeback

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.views.layout.swipeback.WxSwipeBackLayout
import com.views.layout.swipeback.tools.Util.onPanelReset
import com.views.layout.swipeback.tools.Util.onPanelSlide

class WxSwipeBackLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : SwipeBackLayout(context, attrs, defStyleAttr) {

    companion object {
        private val TAG = WxSwipeBackLayout::class.java.simpleName
    }

    override var directionMode: Int
        get() = super.directionMode
        set(direction) {
            super.directionMode = direction
            require(direction == FROM_LEFT) { "The direction of WxSwipeBackLayout must be FROM_LEFT" }
        }

    init {
        val defaultSwipeBackListener: OnSwipeBackListener = object : OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View?, swipeBackFraction: Float, swipeBackFactor: Float) {
                invalidate()
                onPanelSlide(swipeBackFraction)
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                }
                onPanelReset()
            }
        }
        setSwipeBackListener(defaultSwipeBackListener)
    }
}
