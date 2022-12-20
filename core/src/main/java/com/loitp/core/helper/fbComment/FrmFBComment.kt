package com.loitp.core.helper.fbComment

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import com.loitpcore.BuildConfig
import com.loitpcore.R
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.common.Constants
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.l_frm_fb_cmt.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FrmFBComment")
class FrmFBComment : BaseFragment() {

    private var isLoading: Boolean = false
    private var mWebViewPop: WebView? = null
    private var postUrl: String? = null

    companion object {
        // the default number of comments should be visible
        // on page load.
        private const val NUMBER_OF_COMMENTS = 50
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_fb_cmt
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        commentsWebView.setBackgroundColor(Color.TRANSPARENT)
        commentsWebView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)

        context?.let {
            LUIUtil.setColorProgressBar(
                progressBar = progressBar,
                color = LAppResource.getColor(R.color.colorPrimary)
            )
        }
        val bundle = arguments ?: return
        postUrl = bundle.getString(Constants.FACEBOOK_COMMENT_URL)
        if (BuildConfig.DEBUG) {
            postUrl = "https://www.androidhive.info/2016/06/android-firebase-integrate-analytics/"
        }
        if (postUrl.isNullOrEmpty()) {
            rlWebview.visibility = View.GONE
        } else {
            setLoading(true)
            loadComments()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadComments() {
        commentsWebView.apply {
            webViewClient = UriWebViewClient()
            webChromeClient = UriChromeClient()
            settings.javaScriptEnabled = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.domStorageEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.setSupportMultipleWindows(true)
            settings.setSupportZoom(false)
            settings.builtInZoomControls = false
            CookieManager.getInstance().setAcceptCookie(true)
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

            // facebook comment widget including the article url
            val html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                    "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                    "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                    "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                    "</div> </body> </html>"

            loadDataWithBaseURL("http://www.nothing.com", html, "text/html", "UTF-8", null)
            minimumHeight = 200
        }
    }

    private fun setLoading(isLoading: Boolean) {
        this.isLoading = isLoading
        if (isLoading) {
            LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE)
        } else {
            LUIUtil.setDelay(
                mls = 1000,
                runnable = {
                    LUIUtil.setProgressBarVisibility(
                        progressBar = progressBar,
                        visibility = View.GONE
                    )
                }
            )
        }
    }

    private inner class UriWebViewClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val host = Uri.parse(url).host
            return host != "m.facebook.com"
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            // val host = Uri.parse(url).host
            setLoading(false)
            if (url.contains("/plugins/close_popup.php?reload")) {
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Do something after 100ms
                    rlWebview.removeView(mWebViewPop)
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
        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message
        ): Boolean {
            mWebViewPop = context?.let { WebView(it) }
            mWebViewPop?.let {
                it.isVerticalScrollBarEnabled = false
                it.isHorizontalScrollBarEnabled = false
                it.webViewClient = UriWebViewClient()
                it.webChromeClient = this
                it.settings.javaScriptEnabled = true
                it.settings.domStorageEnabled = true
                it.settings.setSupportZoom(false)
                it.settings.builtInZoomControls = false
                it.settings.setSupportMultipleWindows(true)
                it.layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                rlWebview.addView(mWebViewPop)
            }
            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = mWebViewPop
            resultMsg.sendToTarget()
            return true
        }

        override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
            logD("onConsoleMessage: " + cm.message())
            return true
        }

        override fun onCloseWindow(window: WebView) {}
    }
}
