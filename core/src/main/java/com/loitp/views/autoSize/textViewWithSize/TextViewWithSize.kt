package com.loitp.views.autoSize.textViewWithSize

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.loitp.R
import com.loitp.core.utilities.LUIUtil

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class TextViewWithSize : AppCompatTextView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
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
        init()
    }

    private fun init() {
        LUIUtil.setTextShadow(textView = this, color = Color.RED)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.txt_medium))
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.txt_small))
        }
    }
}
