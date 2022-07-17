package com.loitpcore.views.textView.selectable

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class ObservableScrollView : ScrollView {
    private var mOnScrollChangedListeners: ArrayList<OnScrollChangedListener>? = null

    var customTextView: CustomTextView? = null
        private set

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    fun init() {
        mOnScrollChangedListeners = ArrayList(2)
        customTextView = CustomTextView(context)
        this.addView(
            customTextView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        )
    }

    fun setText(text: String?) {
        customTextView?.setText(text, TextView.BufferType.SPANNABLE)
    }

    fun selectAll() {
        customTextView?.showSelectionControls(0, customTextView?.text.toString().length)
    }

    fun hideCursor() {
        customTextView?.hideCursor()
    }

    fun addOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener) {
        mOnScrollChangedListeners?.add(onScrollChangedListener)
    }

    fun removeOnScrollChangedListener(onScrollChangedListener: OnScrollChangedListener) {
        mOnScrollChangedListeners?.remove(onScrollChangedListener)
    }

    override fun onScrollChanged(x: Int, y: Int, oldx: Int, oldy: Int) {
        super.onScrollChanged(x, y, oldx, oldy)
        mOnScrollChangedListeners?.let {
            for (listener in it) {
                listener.onScrollChanged(this, x, y, oldx, oldy)
            }
        }
    }
}
