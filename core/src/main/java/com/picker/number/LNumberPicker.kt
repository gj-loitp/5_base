package com.picker.number

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.R
import kotlinx.android.synthetic.main.view_l_number_picker.view.*

class LNumberPicker : RelativeLayout {
    private val TAG = javaClass.simpleName

    private var callBack: Callback? = null

    fun setCallback(callBack: Callback?) {
        this.callBack = callBack
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    interface Callback {
        fun onValueChangedNumberPicker(h: String, m: String, s: String)
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_number_picker, this)

        numberPickerH.minValue = 0
        numberPickerH.maxValue = 23

        numberPickerM.minValue = 0
        numberPickerM.maxValue = 59

        numberPickerS.minValue = 0
        numberPickerS.maxValue = 59

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

    fun setValue(valueH: Int, valueM: Int, valueS: Int) {
        if (valueH < 0 || valueH > 23) {
            throw IllegalArgumentException("0 <= ValueH <= 23")
        }
        if (valueM < 0 || valueM > 59) {
            throw IllegalArgumentException("0 <= valueM <= 59")
        }
        if (valueS < 0 || valueS > 59) {
            throw IllegalArgumentException("0 <= ValueS <= 59")
        }
        numberPickerH.value = valueH
        numberPickerM.value = valueM
        numberPickerS.value = valueS
        cal()
    }
}
