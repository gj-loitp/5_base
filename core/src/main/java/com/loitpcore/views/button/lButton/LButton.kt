package com.loitpcore.views.button.lButton

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton
import com.loitpcore.R

class LButton : AppCompatImageButton {

    private var pressedDrawable = R.drawable.l_circle_black

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val maskedAction = event.actionMasked
        if (maskedAction == MotionEvent.ACTION_DOWN) {
            this.setBackgroundResource(pressedDrawable)
        } else if (maskedAction == MotionEvent.ACTION_UP) {
            this.setBackgroundResource(0)
        }
        return super.onTouchEvent(event)
    }

    fun setPressedDrawable(pressedDrawable: Int) {
        this.pressedDrawable = pressedDrawable
    }
}
