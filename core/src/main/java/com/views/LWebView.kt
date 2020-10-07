package com.views

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.core.utilities.LLog

class LWebView : WebView {

    companion object {
        const val logTag = "loitppLWebView"
    }

    private fun logD(msg: String) {
        LLog.d(logTag, msg)
    }

    interface OnScrollChangedCallback {
        fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int)
        fun onScrollTopToBottom()
        fun onScrollBottomToTop()
        fun onProgressChanged(progress: Int)
    }

    var onScrollChangedCallback: OnScrollChangedCallback? = null

    private var isScrollBottomToTop = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        settings.javaScriptEnabled = true

        //load the page with cache
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //return true load with system-default-browser or other browsers, false with your webView
                logD("shouldOverrideUrlLoading url $url")
                return false
            }

        }

        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                logD("onProgressChanged: $newProgress")
                onScrollChangedCallback?.onProgressChanged(newProgress)
            }
        }

        //default disable copy content
        setEnableCopyContent(false)
    }

    fun setEnableCopyContent(isEnableCopyContent: Boolean) {
        if (isEnableCopyContent) {
            setOnLongClickListener { false }
            isLongClickable = true
        } else {
            setOnLongClickListener { true }
            isLongClickable = false
        }
    }

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
}
