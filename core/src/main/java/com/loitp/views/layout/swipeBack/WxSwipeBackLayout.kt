package com.loitp.views.layout.swipeBack

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.loitp.views.layout.swipeBack.tools.Util.onPanelReset
import com.loitp.views.layout.swipeBack.tools.Util.onPanelSlide

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class WxSwipeBackLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SwipeBackLayout(context, attrs, defStyleAttr) {

    override var directionMode: Int
        get() = super.directionMode
        set(direction) {
            super.directionMode = direction
            require(direction == FROM_LEFT) { "The direction of WxSwipeBackLayout must be FROM_LEFT" }
        }

    init {
        val defaultSwipeBackListener: OnSwipeBackListener = object : OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
                invalidate()
                onPanelSlide(swipeBackFraction)
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()//correct
                }
                onPanelReset()
            }
        }
        setSwipeBackListener(defaultSwipeBackListener)
    }
}
