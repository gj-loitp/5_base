package com.loitpcore.views.layout.appBar

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * Created by Loitp on 05,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LAppBarLayout : AppBarLayout, AppBarLayout.OnOffsetChangedListener {
    private var state: State? = null
    private var onStateChangeListener: OnStateChangeListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (layoutParams !is CoordinatorLayout.LayoutParams || parent !is CoordinatorLayout) {
            throw IllegalStateException("MyAppBarLayout must be a direct child of CoordinatorLayout.")
        }
        addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        when {
            verticalOffset == 0 -> {
                if (state != State.EXPANDED) {
                    onStateChangeListener?.onStateChange(State.EXPANDED)
                }
                state = State.EXPANDED
            }
            abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                if (state != State.COLLAPSED) {
                    onStateChangeListener?.onStateChange(State.COLLAPSED)
                }
                state = State.COLLAPSED
            }
            else -> {
                if (state != State.IDLE) {
                    onStateChangeListener?.onStateChange(State.IDLE)
                }
                state = State.IDLE
            }
        }
    }

    fun setOnStateChangeListener(listener: OnStateChangeListener) {
        this.onStateChangeListener = listener
    }

    interface OnStateChangeListener {
        fun onStateChange(toolbarChange: State)
    }

    enum class State {
        COLLAPSED,
        EXPANDED,
        IDLE
    }
}
