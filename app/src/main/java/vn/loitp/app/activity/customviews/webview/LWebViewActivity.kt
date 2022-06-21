package vn.loitp.app.activity.customviews.webview

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import com.views.LWebViewAdblock
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_web_view.*
import vn.loitp.app.R

@LogTag("LWebViewActivity")
@IsFullScreen(false)
class LWebViewActivity : BaseFontActivity() {

    var isDetectButtonClickAsset = false;
    var isDetectButtonClickWeb = false;

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_web_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        wv.settings.javaScriptEnabled = true
        wv.settings.domStorageEnabled = true
        wv.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                logE("shouldOverrideUrlLoading $url}")
                view.loadUrl(url)
                return true
            }
        }

        lWebView.callback = object : LWebViewAdblock.Callback {
            override fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int) {
            }

            override fun onScrollTopToBottom() {
                logD("onScrollTopToBottom")
            }

            override fun onScrollBottomToTop() {
                logD("onScrollBottomToTop")
            }

            override fun onProgressChanged(progress: Int) {
                logD("onProgressChanged $progress")
                pb.progress = progress
                if (progress == 100) {
                    pb.visibility = View.GONE
                } else {
                    pb.visibility = View.VISIBLE
                }
            }

            override fun shouldOverrideUrlLoading(url: String) {

                // detect click button submit
                if (isDetectButtonClickAsset) {
                    lWebView.addJavascriptInterface(object : Any() {
                        @JavascriptInterface
                        @Throws(java.lang.Exception::class)
                        fun performClick() {
                            showLongInformation("Login clicked");
                        }
                    }, "login")
                }

                if (isDetectButtonClickWeb) {
                    lWebView.addJavascriptInterface(object : Any() {
                        @JavascriptInterface
                        @Throws(java.lang.Exception::class)
                        fun performClick() {
                            showLongInformation("Print clicked");
                        }
                    }, "printOrder")
                }
            }
        }

        btLoadUrl.setSafeOnClickListener {
            lWebView.isVisible = true
            wv.isVisible = false
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            lWebView.loadUrl("https://vnexpress.net/facebook-hay-google-manh-hon-4226827.html/")
        }
        btLoadData.setSafeOnClickListener {
            lWebView.isVisible = true
            wv.isVisible = false
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            lWebView.loadDataString(bodyContent = "Hello, world!")
        }
        btLoadDataCustom.setSafeOnClickListener {
            lWebView.isVisible = true
            wv.isVisible = false
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            val fontSizePx = LAppResource.getDimenValue(R.dimen.txt_small)
            val paddingPx = LAppResource.getDimenValue(R.dimen.margin_padding_small)
            lWebView.loadDataString(
                bodyContent = getString(R.string.large_dummy_text),
                backgroundColor = "black",
                textColor = "white",
                textAlign = "justify",
                fontSizePx = fontSizePx,
                paddingPx = paddingPx
            )
        }
        btLoadDataFromAsset.setSafeOnClickListener {
            lWebView.isVisible = true
            wv.isVisible = false
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            lWebView.loadUrl("file:///android_asset/web/policy.html")
        }
        btLoadDataFromAssetAndClick.setSafeOnClickListener {
            lWebView.isVisible = true
            wv.isVisible = false
            isDetectButtonClickAsset = true
            isDetectButtonClickWeb = false
            lWebView.loadUrl("file:///android_asset/web/index.html")
        }
        btLoadDataFromWebAndClick.setSafeOnClickListener {
            lWebView.isVisible = false
            wv.isVisible = true
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = true
            logE(">>>btLoadDataFromWebAndClick")
            wv.loadUrl("https://bizman.dikauri.com/dashboard")
//            wv.loadUrl("https://vnexpress.net/facebook-hay-google-manh-hon-4226827.html/")
        }
        swEnableCopyContent.setOnCheckedChangeListener { _, isChecked ->
            lWebView.setEnableCopyContent(isChecked)
        }
        swEnableDarkMode.setOnCheckedChangeListener { _, isChecked ->
            try {
                lWebView.setDarkMode(isChecked)
            } catch (e: Exception) {
                showSnackBarError("setOnCheckedChangeListener $e")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && lWebView.canGoBack()) {
            lWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
