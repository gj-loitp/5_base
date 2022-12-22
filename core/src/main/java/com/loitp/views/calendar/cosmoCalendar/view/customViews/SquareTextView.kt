package com.loitp.views.calendar.cosmoCalendar.view.customViews

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class SquareTextView(context: Context) : AppCompatTextView(context) {
    // Square view
    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
