package com.loitpcore.views.et.autoResize

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.loitpcore.R
import java.util.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class AutoFitLayout : FrameLayout {
    private var mEnabled = false
    private var mMinTextSize = 0f
    private var mPrecision = 0f
    private val mHelpers = WeakHashMap<View, AutoFitHelper>()

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) {
        var sizeToFit = true
        var minTextSize = -1
        var precision = -1f
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(
                /* set = */ attrs,
                /* attrs = */ R.styleable.AutoFitEditText,
                /* defStyleAttr = */ defStyle,
                /* defStyleRes = */ 0
            )
            sizeToFit = typedArray.getBoolean(R.styleable.AutoFitEditText_sizeToFit, sizeToFit)
            minTextSize = typedArray.getDimensionPixelSize(
                R.styleable.AutoFitEditText_minTextSize,
                minTextSize
            )
            precision = typedArray.getFloat(R.styleable.AutoFitEditText_precision, precision)
            typedArray.recycle()
        }
        mEnabled = sizeToFit
        mMinTextSize = minTextSize.toFloat()
        mPrecision = precision
    }

    override fun addView(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams
    ) {
        super.addView(child, index, params)
        val textView = child as TextView
        val helper = AutoFitHelper.create(textView).setEnabled(mEnabled)
        if (mPrecision > 0) {
            helper.precision = mPrecision
        }
        if (mMinTextSize > 0) {
            helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, mMinTextSize)
        }
        mHelpers[textView] = helper
    }

    /**
     * Returns the [AutoFitHelper] for this child View.
     */
    @Suppress("unused")
    fun getAutoFitHelper(textView: TextView): AutoFitHelper? {
        return mHelpers[textView]
    }

    /**
     * Returns the [AutoFitHelper] for this child View.
     */
    @Suppress("unused")
    fun getAutoFitHelper(index: Int): AutoFitHelper? {
        return mHelpers[getChildAt(index)]
    }
}
