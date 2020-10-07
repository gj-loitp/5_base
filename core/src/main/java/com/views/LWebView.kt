package com.views

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class LWebView : WebView {

    var onScrollChangedCallback: OnScrollChangedCallback? = null

    private var isScrollBottomToTop = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)

        onScrollChangedCallback?.onScroll(l, t, oldl, oldt)

        if (oldt > t) {
//            logD("bottom to top")
            if (!isScrollBottomToTop) {
                isScrollBottomToTop = true
                onScrollChangedCallback?.onScrollBottomToTop()
            }
        } else {
//            logD("top to bottom")
            if (isScrollBottomToTop) {
                isScrollBottomToTop = false
                onScrollChangedCallback?.onScrollTopToBottom()
            }
        }
    }

    interface OnScrollChangedCallback {
        fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int)
        fun onScrollTopToBottom()
        fun onScrollBottomToTop()
    }
}
