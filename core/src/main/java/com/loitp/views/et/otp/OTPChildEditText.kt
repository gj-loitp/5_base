package com.loitp.views.et.otp

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.loitp.R

internal class OTPChildEditText : androidx.appcompat.widget.AppCompatEditText {

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        init(context)
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        isCursorVisible = false
//        setTextColor(context.resources.getColor(R.color.transparent))
        setTextColor(ContextCompat.getColor(context, R.color.transparent))
//        setBackgroundDrawable(null)
        background = null
        inputType = InputType.TYPE_CLASS_NUMBER
        setSelectAllOnFocus(false)
        setTextIsSelectable(false)
    }

    public override fun onSelectionChanged(
        start: Int,
        end: Int
    ) {

        val text = text
        text?.let { t ->
            if (start != t.length || end != t.length) {
                setSelection(t.length, t.length)
                return
            }
        }

        super.onSelectionChanged(start, end)
    }

}