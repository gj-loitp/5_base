package vn.loitp.app.activity.customviews.webview

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import com.views.LWebView
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_web_view.*
import vn.loitp.app.R


@LogTag("LWebViewActivity")
@IsFullScreen(false)
class LWebViewActivity : BaseFontActivity() {

    var isDetectButtonClick = false;

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_web_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        webView.callback = object : LWebView.Callback {
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
                if (isDetectButtonClick) {
                    webView.addJavascriptInterface(object : Any() {
                        @JavascriptInterface
                        @Throws(java.lang.Exception::class)
                        fun performClick() {
                            showLongInformation("Login clicked");
                        }
                    }, "login")
                }
            }
        }

        btLoadUrl.setSafeOnClickListener {
            isDetectButtonClick = false
            webView.loadUrl("https://vnexpress.net/facebook-hay-google-manh-hon-4226827.html/")
        }
        btLoadData.setSafeOnClickListener {
            isDetectButtonClick = false
            webView.loadDataString(bodyContent = "Hello, world!")
        }
        btLoadDataCustom.setSafeOnClickListener {
            isDetectButtonClick = false
            val fontSizePx = LAppResource.getDimenValue(R.dimen.txt_small)
            val paddingPx = LAppResource.getDimenValue(R.dimen.margin_padding_small)
            webView.loadDataString(
                bodyContent = getString(R.string.large_dummy_text),
                backgroundColor = "black",
                textColor = "white",
                textAlign = "justify",
                fontSizePx = fontSizePx,
                paddingPx = paddingPx
            )
        }
        btLoadDataFromAsset.setSafeOnClickListener {
            isDetectButtonClick = false
            webView.loadUrl("file:///android_asset/web/policy.html")
        }
        btLoadDataFromAssetAndClick.setSafeOnClickListener {
            isDetectButtonClick = true
            webView.loadUrl("file:///android_asset/web/index.html")
        }
        swEnableCopyContent.setOnCheckedChangeListener { _, isChecked ->
            webView.setEnableCopyContent(isChecked)
        }
        swEnableDarkMode.setOnCheckedChangeListener { _, isChecked ->
            try {
                webView.setDarkMode(isChecked)
            } catch (e: Exception) {
                showSnackBarError("setOnCheckedChangeListener $e")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
