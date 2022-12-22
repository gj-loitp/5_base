package vn.loitp.app.activity.customviews.webview.lwebView

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.wv.LWebViewAdblock
import kotlinx.android.synthetic.main.activity_web_view.*
import vn.loitp.R

@LogTag("LWebViewActivity")
@IsFullScreen(false)
class LWebViewActivity : BaseFontActivity() {

    private var isDetectButtonClickAsset = false
    private var isDetectButtonClickWeb = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_web_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onDetectClick()
        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = LWebViewActivity::class.java.simpleName
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
                    logD(">>>onProgressChanged finish ${lWebView.url}")
                } else {
                    pb.visibility = View.VISIBLE
                }
            }

            override fun shouldOverrideUrlLoading(url: String) {
                logE(">shouldOverrideUrlLoading $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                logD(">onPageFinished $url")
            }
        }

        btLoadUrl.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            lWebView.loadUrl("https://vnexpress.net/facebook-hay-google-manh-hon-4226827.html/")
        }
        btLoadData.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            lWebView.loadDataString(bodyContent = "Hello, world!")
        }
        btLoadDataCustom.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
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
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            lWebView.loadUrl("file:///android_asset/web/policy.html")
        }
        btLoadDataFromAssetAndClick.setSafeOnClickListener {
            isDetectButtonClickAsset = true
            isDetectButtonClickWeb = false
            onDetectClick()
            lWebView.loadUrl("file:///android_asset/web/index.html")
        }
        btLoadDataFromWebAndClick.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = true
            onDetectClick()
            logE(">>>btLoadDataFromWebAndClick")
            lWebView.loadUrl("https://bizman.dikauri.com/signin")
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

    private fun onDetectClick() {
        logE(">>>onDetectClick")
        // detect click button submit
        if (isDetectButtonClickAsset) {
            lWebView.addJavascriptInterface(object : Any() {
                @JavascriptInterface
                @Throws(java.lang.Exception::class)
                @Suppress("unused")
                fun performClick(id: String) {
                    showLongInformation("Login print order id: $id")
                }
            }, "handlePrintOrder")
        }

        if (isDetectButtonClickWeb) {
            lWebView.addJavascriptInterface(object : Any() {
                @JavascriptInterface
                @Throws(java.lang.Exception::class)
                @Suppress("unused")
                fun performClick(id: String) {
                    logE("isDetectButtonClickWeb order id: $id")
                    showLongInformation("isDetectButtonClickWeb order id: $id")
                }
            }, "handlePrintOrder")
        }
    }
}
