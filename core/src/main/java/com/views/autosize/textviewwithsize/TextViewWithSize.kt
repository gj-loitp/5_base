package com.views.autosize.textviewwithsize

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import com.R
import com.core.utilities.LUIUtil

class TextViewWithSize : AppCompatTextView {
    private val logTag = javaClass.simpleName

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LUIUtil.setTextShadow(textView = this, color = Color.RED)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.txt_18))
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.txt_12))
        }
    }
}
