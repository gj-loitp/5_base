package com.loitpcore.views.calendar.cosmoCalendar.view.customViews

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView

class SquareTextView(context: Context) : AppCompatTextView(context) {
    // Square view
    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
