package com.loitpcore.views.scrollView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView

class LHorizontalScrollView : HorizontalScrollView {

    interface ScrollListener {
        fun onScrollChange(view: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int)
    }

    private var mScrollListener: LScrollView.ScrollListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    fun setOnScrollListener(scrollListener: LScrollView.ScrollListener) {
        this.mScrollListener = scrollListener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        mScrollListener?.onScrollChange(this, l, t, oldl, oldt)
    }
}
