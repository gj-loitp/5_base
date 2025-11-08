package com.loitp.views.answerView

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.loitp.R
import com.loitp.core.ext.getColor
import com.loitp.core.ext.getDrawable

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class OneAnswerView : LinearLayout {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
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
        init()
    }

    @Suppress("unused")
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    @JvmField
    var active = false

    private lateinit var chooice: TextView

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.l_v_answer_view, this, true)
        chooice = findViewById(R.id.chooice)
    }

    fun setActive(
        active: Boolean,
        lAnswerView: LAnswerView
    ) {
        if (active != this.active) {
            this.active = active
            if (active) {
                if (lAnswerView.awShowTextWhenActive) {
                    chooice.setTextColor(getColor(R.color.colorPrimary))
                } else {
                    chooice.visibility = INVISIBLE
                }
                getChildAt(0).background = getDrawable(R.drawable.l_answer_circle_ac)
            } else {
                if (lAnswerView.awShowTextWhenActive) {
                    chooice.setTextColor(getColor(R.color.black))
                } else {
                    chooice.visibility = VISIBLE
                }
                getChildAt(0).background = getDrawable(R.drawable.l_answer_circle)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setIndex(index: Int) {
        chooice.text = (('A'.code + index).toChar()).toString()
    }
}
