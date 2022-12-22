package com.loitp.views.tv.extra

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LToggleLExtraTextView : LExtraTextView {
    private lateinit var state: State
    private var activeDrawableResourceId: Int = 0
    private var activeTint: Int = 0
    private var waitingTint: Int = 0
    private var idleDrawableResourceId: Int = 0
    private var idleTint: Int = 0

    enum class State {
        ACTIVE, WAITING, IDLE
    }

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        if (attrs == null) {
            return
        }

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.LToggleLExtraTextView,
            defStyleAttr,
            0
        )

        state = State.values()[
                typedArray.getInt(
                    R.styleable.LToggleLExtraTextView_ext_txt_state,
                    State.IDLE.ordinal
                )
        ]
        activeDrawableResourceId = typedArray.getResourceId(
            R.styleable.LToggleLExtraTextView_ext_txt_activeDrawable,
            getDrawableResourceId()
        )
        activeTint = typedArray.getColor(R.styleable.LToggleLExtraTextView_ext_txt_activeTint, 0)
        waitingTint =
            typedArray.getColor(R.styleable.LToggleLExtraTextView_ext_txt_waitingTint, Color.DKGRAY)
        idleDrawableResourceId = typedArray.getResourceId(
            R.styleable.LToggleLExtraTextView_ext_txt_idleDrawable,
            getDrawableResourceId()
        )
        idleTint = typedArray.getColor(R.styleable.LToggleLExtraTextView_ext_txt_idleTint, 0)

        typedArray.recycle()
        updateState()
    }

    private fun updateState() {
        when (state) {
            State.ACTIVE -> {
                setTextColor(activeTint)
                setRoundedCornerBorderColor(activeTint)
                setDrawableTint(activeTint)
                setDrawableResourceId(activeDrawableResourceId)
                isEnabled = true
                requestLayout()
            }
            State.WAITING -> {
                setTextColor(waitingTint)
                setRoundedCornerBorderColor(waitingTint)
                setDrawableTint(waitingTint)
                isEnabled = false
                requestLayout()
            }
            State.IDLE -> {
                setTextColor(idleTint)
                setRoundedCornerBorderColor(idleTint)
                setDrawableTint(idleTint)
                setDrawableResourceId(idleDrawableResourceId)
                isEnabled = true
                requestLayout()
            }
        }
    }

    fun getState(): State {
        return state
    }

    fun setState(state: State) {
        this.state = state
        updateState()
    }

    fun toggle() {
        if (state == State.ACTIVE) {
            setState(State.IDLE)
        } else if (state == State.IDLE) {
            setState(State.ACTIVE)
        }
    }
}
