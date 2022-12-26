package com.loitp.picker.number

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.loitp.R
import kotlinx.android.synthetic.main.l_v_number_picker.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LNumberPicker : RelativeLayout {
    private var minValueH = 0
    private var maxValueH = 23
    private var minValueM = 0
    private var maxValueM = 59
    private var minValueS = 0
    private var maxValueS = 59

    private var callBack: Callback? = null

    fun setCallback(callBack: Callback?) {
        this.callBack = callBack
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    interface Callback {
        fun onValueChangedNumberPicker(h: String, m: String, s: String)
    }

    private fun init() {
        View.inflate(context, R.layout.l_v_number_picker, this)
        setMinMaxValue()

        numberPickerH.setOnValueChangedListener { _, _, _ ->
            cal()
        }
        numberPickerM.setOnValueChangedListener { _, _, _ ->
            cal()
        }
        numberPickerS.setOnValueChangedListener { _, _, _ ->
            cal()
        }
    }

    private fun setMinMaxValue() {
        numberPickerH.minValue = minValueH
        numberPickerH.maxValue = maxValueH

        numberPickerM.minValue = minValueM
        numberPickerM.maxValue = maxValueM

        numberPickerS.minValue = minValueS
        numberPickerS.maxValue = maxValueS
    }

    private fun cal() {
        val h = if (numberPickerH.value <= 9) {
            "0" + numberPickerH.value
        } else {
            "" + numberPickerH.value
        }

        val m = if (numberPickerM.value <= 9) {
            "0" + numberPickerM.value
        } else {
            "" + numberPickerM.value
        }

        val s = if (numberPickerS.value <= 9) {
            "0" + numberPickerS.value
        } else {
            "" + numberPickerS.value
        }

        callBack?.onValueChangedNumberPicker(h = h, m = m, s = s)
    }

    fun setMinMaxValue(
        valueH: Int,
        valueM: Int,
        valueS: Int
    ) {
        if (valueH < minValueH || valueH > maxValueH) {
            throw IllegalArgumentException("$minValueH <= ValueH <= $maxValueH")
        }
        if (valueM < minValueM || valueM > maxValueM) {
            throw IllegalArgumentException("$minValueM <= valueM <= $maxValueM")
        }
        if (valueS < minValueS || valueS > maxValueS) {
            throw IllegalArgumentException("$minValueS <= ValueS <= $maxValueS")
        }
        numberPickerH.value = valueH
        numberPickerM.value = valueM
        numberPickerS.value = valueS
        cal()
    }

    fun setMinValue(
        minValueH: Int,
        minValueM: Int,
        minValueS: Int
    ) {
        this.minValueH = minValueH
        this.minValueM = minValueM
        this.minValueS = minValueS

        setMinMaxValue()
    }

    fun setMaxValue(
        maxValueH: Int,
        maxValueM: Int,
        maxValueS: Int
    ) {
        this.maxValueH = maxValueH
        this.maxValueM = maxValueM
        this.maxValueS = maxValueS

        setMinMaxValue()
    }

    fun resetMinMaxValue() {
        minValueH = 0
        maxValueH = 23
        minValueM = 0
        maxValueM = 59
        minValueS = 0
        maxValueS = 59

        setMinMaxValue()
    }
}
