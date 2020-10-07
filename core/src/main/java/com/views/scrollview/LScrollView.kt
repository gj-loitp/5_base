package com.views.scrollview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView

class LScrollView : ScrollView {

    private var mScrollListener: ScrollListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setOnScrollListener(scrollListener: ScrollListener) {
        this.mScrollListener = scrollListener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        mScrollListener?.onScrollChange(this, l, t, oldl, oldt)
    }

    interface ScrollListener {
        fun onScrollChange(view: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int)
    }
}