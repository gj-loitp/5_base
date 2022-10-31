package com.loitpcore.views.answerView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.loitpcore.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LAnswerView : LinearLayout, View.OnClickListener {

    interface OnAnswerChange {
        fun onAnswerChange(view: LAnswerView?, index: Int)
    }

    var awShowTextWhenActive = false
    private var awChangeOnClick = false
    private var awCanCancelAnswer = false
    private var numberTextView: TextView? = null
    private lateinit var viewler: Array<OneAnswerView?>
    var change: OnAnswerChange? = null

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
//        init(context, attrs)
//    }

    fun setOnAnswerChange(event: OnAnswerChange?) {
        change = event
    }

    // numberOfAnswer max is 6
    fun init(
        number: Int,
        numberOfAnswer: Int,
        isShowNumber: Boolean,
        isCanCancelAnswer: Boolean,
        isChangeOnClick: Boolean,
        isShowTextWhenActive: Boolean
    ) {
        orientation = HORIZONTAL
        awShowTextWhenActive = isShowTextWhenActive
        awChangeOnClick = isChangeOnClick
        awCanCancelAnswer = isCanCancelAnswer
        if (isShowNumber) {
            numberTextView = LayoutInflater.from(context)
                .inflate(R.layout.l_view_answer_number, this, false) as TextView
            setNumber(number)
            addView(numberTextView)
        }
        viewler = arrayOfNulls(numberOfAnswer)
        for (i in 0 until numberOfAnswer) {
            viewler[i] = LayoutInflater.from(context)
                .inflate(R.layout.l_view_answer_one_view, this, false) as OneAnswerView
            viewler[i]?.let {
                it.setIndex(i)
                it.setOnClickListener(this)
                addView(it)
            }
        }
    }

    private fun init(attrs: AttributeSet?) {
        orientation = HORIZONTAL
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.LAnswerView, 0, 0)
            awShowTextWhenActive =
                a.getBoolean(R.styleable.LAnswerView_aw_ShowTextWhenActive, false)
            awChangeOnClick = a.getBoolean(R.styleable.LAnswerView_aw_changeOnClick, true)
            awCanCancelAnswer = a.getBoolean(R.styleable.LAnswerView_aw_canCancelAnswer, true)
            val count = a.getInt(R.styleable.LAnswerView_aw_NumberOfAnswers, 5)
            if (a.getBoolean(R.styleable.LAnswerView_aw_ShowNumber, false)) {
                numberTextView = LayoutInflater.from(context)
                    .inflate(R.layout.l_view_answer_number, this, false) as TextView
                setNumber(a.getInt(R.styleable.LAnswerView_aw_Number, 1))
                addView(numberTextView)
            }
            a.recycle()
            viewler = arrayOfNulls(count)
            for (i in 0 until count) {
                viewler[i] = LayoutInflater.from(context)
                    .inflate(R.layout.l_view_answer_one_view, this, false) as OneAnswerView
                viewler[i]?.let {
                    it.setIndex(i)
                    it.setOnClickListener(this)
                    addView(it)
                }
            }
        }
    }

    fun resize(count: Int) {
        val index = activeIndex
        removeAllViews()
        numberTextView?.let {
            addView(it)
        }
        for (oav in viewler) {
            oav?.setOnClickListener(null)
        }
        viewler = arrayOfNulls(count)
        for (i in 0 until count) {
            viewler[i] = LayoutInflater.from(context)
                .inflate(R.layout.l_view_answer_one_view, this, false) as OneAnswerView

            viewler[i]?.let {
                it.setIndex(i)
                it.setOnClickListener(this)
                it.setActive(index == i, this)
                addView(it)
            }
        }
    }

    fun setNumber(number: Int) {
        numberTextView?.text = number.toString()
    }

    var activeChar: Char
        get() = ('A'.code + activeIndex).toChar()
        set(index) {
            activeIndex = index - 'A'
        }

    private var activeIndex: Int
        get() {
            for (i in 0 until childCount) {
                if (viewler[i]?.active == true) return i
            }
            return -1
        }
        set(index) {
            for (i in viewler.indices) {
                viewler[i]?.setActive(index == i, this)
            }
        }

    override fun onClick(v: View) {
        for (i in viewler.indices) {
            if (viewler[i] == v) {
                if (awChangeOnClick) {
                    v.setActive(!awCanCancelAnswer || !v.active, this)
                }
                change?.onAnswerChange(this, if (v.active) i else -1)
            } else if (awChangeOnClick) {
                viewler[i]?.setActive(false, this)
            }
        }
    }
}
