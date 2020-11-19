package com.views.answerview.internal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.R
import com.core.utilities.LAppResource
import com.views.answerview.LAnswerView
import kotlinx.android.synthetic.main.l_answer_view.view.*

class OneAnswerView : LinearLayout {

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    @JvmField
    var active = false
    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.l_answer_view, this, true)
    }

    fun setActive(active: Boolean, lAnswerView: LAnswerView) {
        if (active != this.active) {
            this.active = active
            if (active) {
                if (lAnswerView.awShowTextWhenActive) {
                    chooice.setTextColor(LAppResource.getColor(R.color.colorPrimary))
                } else {
                    chooice.visibility = INVISIBLE
                }
                getChildAt(0).background = LAppResource.getDrawable(R.drawable.l_answer_circle_ac)
            } else {
                if (lAnswerView.awShowTextWhenActive) {
                    chooice.setTextColor(LAppResource.getColor(R.color.black))
                } else {
                    chooice.visibility = VISIBLE
                }
                getChildAt(0).background = LAppResource.getDrawable(R.drawable.l_answer_circle)
            }
        }
    }

    fun setIndex(index: Int) {
        chooice.text = (('A'.toInt() + index).toChar()).toString()
//        chooice.text = "$index"
    }
}
