package com.loitp.views.wv

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.*
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.loitp.core.ext.d
import org.adblockplus.libadblockplus.android.webview.AdblockWebView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@SuppressLint("SetJavaScriptEnabled", "RequiresFeature")
class LWebViewAdblock : AdblockWebView {

    companion object {
        const val logTag = "LWebView"
    }

    private fun logD(msg: String) {
        d(logTag, msg)
    }

    interface Callback {
        fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int)
        fun onScrollTopToBottom()
        fun onScrollBottomToTop()
        fun onProgressChanged(progress: Int)
        fun shouldOverrideUrlLoading(url: String)
        fun onPageFinished(view: WebView?, url: String?)
    }

    var callback: Callback? = null
    var currentProgress: Int = 0

    private var isScrollBottomToTop = true

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    )

    init {
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        // load the page with cache
        settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        shouldOverrideUrlLoading(shouldOverrideUrlLoading = false)

        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                logD("onProgressChanged: $newProgress")

                if (currentProgress != newProgress) {
                    callback?.onProgressChanged(newProgress)
                    currentProgress = newProgress
                }
            }
        }

        // default disable copy content
        setEnableCopyContent(false)
    }

    fun shouldOverrideUrlLoading(shouldOverrideUrlLoading: Boolean) {
        webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                // return true load with system-default-browser or other browsers, false with your webView
//                logD("shouldOverrideUrlLoading url $url")
//                callback?.shouldOverrideUrlLoading(url = url)
//                return shouldOverrideUrlLoading
//            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                logD("shouldOverrideUrlLoading url $url")
                url?.let {
                    callback?.shouldOverrideUrlLoading(url = it)
                }
                return shouldOverrideUrlLoading
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                callback?.onPageFinished(view, url)
            }
        }
    }

    fun setEnableCopyContent(isEnableCopyContent: Boolean) {
        isLongClickable = if (isEnableCopyContent) {
            setOnLongClickListener { false }
            true
        } else {
            setOnLongClickListener { true }
            false
        }
    }

    override fun onScrollChanged(
        l: Int,
        t: Int,
        oldl: Int,
        oldt: Int
    ) {
        super.onScrollChanged(l, t, oldl, oldt)

        callback?.onScroll(l, t, oldl, oldt)

        if (oldt > t) {
//            logD("bottom to top")
            if (!isScrollBottomToTop) {
                isScrollBottomToTop = true
                callback?.onScrollBottomToTop()
            }
        } else {
//            logD("top to bottom")
            if (isScrollBottomToTop) {
                isScrollBottomToTop = false
                callback?.onScrollTopToBottom()
            }
        }
    }

    fun loadDataString(bodyContent: String) {
        loadData("<html><body>$bodyContent</body></html>", "text/html", "UTF-8")
    }

    fun loadDataString(
        bodyContent: String = "",
        backgroundColor: String = "coral",
        textColor: String = "black",
        textAlign: String = "justify",
        fontSizePx: Int = 15,
        paddingPx: Int = 15
    ) {
        val style = """<style type="text/css">
@font-face{
  font-family: MyFont;
  src: url("file:///android_asset/fonts/android_font.TTF");
}
body {
  background-color: $backgroundColor;
  color: $textColor;
  font-family: MyFont;
  text-align: $textAlign;
  font-size: ${fontSizePx}px;
  padding-top: ${paddingPx}px;
  padding-right: ${paddingPx}px;
  padding-bottom: ${paddingPx}px;
  padding-left: ${paddingPx}px;
}
</style>"""
        val bodyContentString = "<html>$style<body>$bodyContent</body></html>"
//        logD("bodyContentString $bodyContentString")
        this.loadDataWithBaseURL(null, bodyContentString, "text/html", "UTF-8", "about:blank")
    }

    @Suppress("DEPRECATION")
    fun setTextSize(sizePercent: Int = 100) {
        logD("setTextSize sizePercent $sizePercent")
        val currentApiVersion = Build.VERSION.SDK_INT
        if (currentApiVersion <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            settings.textSize = WebSettings.TextSize.NORMAL
        } else {
            settings.textZoom = sizePercent
        }
    }

    fun setDarkMode(isDarkMode: Boolean) {
        val isFeatureSupported =
            WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK_STRATEGY)
        logD("isFeatureSupported $isFeatureSupported")
        if (isFeatureSupported) {
            if (isDarkMode) {
                //TODO setForceDark android 13 not work
                WebSettingsCompat.setForceDark(this.settings, WebSettingsCompat.FORCE_DARK_ON)
            } else {
                WebSettingsCompat.setForceDark(this.settings, WebSettingsCompat.FORCE_DARK_OFF)
            }
        } else {
            throw IllegalArgumentException("This device is not supported darm mode setting")
        }
    }
}
