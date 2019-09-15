package com.core.helper.fbcomment

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.R
import com.core.base.BaseFragment
import com.core.common.Constants
import com.core.utilities.LLog
import com.core.utilities.LUIUtil

class FrmFBComment : BaseFragment() {
    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private var mContainer: RelativeLayout? = null
    private var mWebViewComments: WebView? = null
    private var progressBar: ProgressBar? = null
    private var isLoading: Boolean = false
    private var mWebviewPop: WebView? = null
    private var postUrl: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mWebViewComments = view.findViewById(R.id.commentsView)
        mWebViewComments?.let {
            it.setBackgroundColor(Color.TRANSPARENT)
            it.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        }

        mContainer = view.findViewById(R.id.webview_frame)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar?.let {
            LUIUtil.setColorProgressBar(it, ContextCompat.getColor(activity!!, R.color.colorPrimary))
        }
        val bundle = arguments ?: return
        postUrl = bundle.getString(Constants.FACEBOOK_COMMENT_URL)
        if (Constants.IS_DEBUG) {
            postUrl = "https://www.androidhive.info/2016/06/android-firebase-integrate-analytics/"
        }
        if (postUrl.isNullOrEmpty()) {
            mContainer?.visibility = View.GONE
        } else {
            setLoading(true)
            loadComments()
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_fb_cmt
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadComments() {
        mWebViewComments?.let {
            it.webViewClient = UriWebViewClient()
            it.webChromeClient = UriChromeClient()
            it.settings.javaScriptEnabled = true
            it.settings.setAppCacheEnabled(true)
            it.settings.domStorageEnabled = true
            it.settings.javaScriptCanOpenWindowsAutomatically = true
            it.settings.setSupportMultipleWindows(true)
            it.settings.setSupportZoom(false)
            it.settings.builtInZoomControls = false
            CookieManager.getInstance().setAcceptCookie(true)
            if (Build.VERSION.SDK_INT >= 21) {
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                CookieManager.getInstance().setAcceptThirdPartyCookies(it, true)
            }

            // facebook comment widget including the article url
            val html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                    "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                    "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                    "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                    "</div> </body> </html>"

            it.loadDataWithBaseURL("http://www.nothing.com", html, "text/html", "UTF-8", null)
            it.minimumHeight = 200
        }
    }

    private fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (isLoading) {
            LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE)
        } else {
            LUIUtil.setDelay(1000, Runnable {
                LUIUtil.setProgressBarVisibility(progressBar, View.GONE)
            })
        }
    }

    private inner class UriWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val host = Uri.parse(url).host
            return host != "m.facebook.com"
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            //val host = Uri.parse(url).host
            setLoading(false)
            if (url.contains("/plugins/close_popup.php?reload")) {
                val handler = Handler()
                handler.postDelayed({
                    //Do something after 100ms
                    mContainer?.removeView(mWebviewPop)
                    loadComments()
                }, 600)
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            setLoading(false)
        }
    }

    private inner class UriChromeClient : WebChromeClient() {

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
            mWebviewPop = WebView(activity)
            mWebviewPop?.let {
                it.isVerticalScrollBarEnabled = false
                it.isHorizontalScrollBarEnabled = false
                it.webViewClient = UriWebViewClient()
                it.webChromeClient = this
                it.settings.javaScriptEnabled = true
                it.settings.domStorageEnabled = true
                it.settings.setSupportZoom(false)
                it.settings.builtInZoomControls = false
                it.settings.setSupportMultipleWindows(true)
                it.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                mContainer?.addView(mWebviewPop)
            }
            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = mWebviewPop
            resultMsg.sendToTarget()
            return true
        }

        override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
            LLog.d(TAG, "onConsoleMessage: " + cm.message())
            return true
        }

        override fun onCloseWindow(window: WebView) {}
    }

    companion object {
        // the default number of comments should be visible
        // on page load.
        private const val NUMBER_OF_COMMENTS = 50
    }
}