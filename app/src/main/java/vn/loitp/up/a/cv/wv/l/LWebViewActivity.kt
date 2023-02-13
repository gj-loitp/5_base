package vn.loitp.up.a.cv.wv.l

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getDimenValue
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.wv.LWebViewAdblock
import vn.loitp.R
import vn.loitp.databinding.AWvBinding

@LogTag("LWebViewActivity")
@IsFullScreen(false)
class LWebViewActivity : BaseActivityFont() {

    private var isDetectButtonClickAsset = false
    private var isDetectButtonClickWeb = false
    private lateinit var binding: AWvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AWvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onDetectClick()
        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = LWebViewActivity::class.java.simpleName
        }
        binding.lWebView.callback = object : LWebViewAdblock.Callback {
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
                binding.pb.progress = progress
                if (progress == 100) {
                    binding.pb.visibility = View.GONE
                    logD(">>>onProgressChanged finish ${binding.lWebView.url}")
                } else {
                    binding.pb.visibility = View.VISIBLE
                }
            }

            override fun shouldOverrideUrlLoading(url: String) {
                logE(">shouldOverrideUrlLoading $url")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                logD(">onPageFinished $url")
            }
        }

        binding.btLoadUrl.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            binding.lWebView.loadUrl("https://vnexpress.net/facebook-hay-google-manh-hon-4226827.html/")
        }
        binding.btLoadData.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            binding.lWebView.loadDataString(bodyContent = "Hello, world!")
        }
        binding.btLoadDataCustom.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            val fontSizePx = getDimenValue(R.dimen.txt_small)
            val paddingPx = getDimenValue(R.dimen.margin_padding_small)
            binding.lWebView.loadDataString(
                bodyContent = getString(R.string.large_dummy_text),
                backgroundColor = "black",
                textColor = "white",
                textAlign = "justify",
                fontSizePx = fontSizePx,
                paddingPx = paddingPx
            )
        }
        binding.btLoadDataFromAsset.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = false
            onDetectClick()
            binding.lWebView.loadUrl("file:///android_asset/web/policy.html")
        }
        binding.btLoadDataFromAssetAndClick.setSafeOnClickListener {
            isDetectButtonClickAsset = true
            isDetectButtonClickWeb = false
            onDetectClick()
            binding.lWebView.loadUrl("file:///android_asset/web/index.html")
        }
        binding.btLoadDataFromWebAndClick.setSafeOnClickListener {
            isDetectButtonClickAsset = false
            isDetectButtonClickWeb = true
            onDetectClick()
            logE(">>>btLoadDataFromWebAndClick")
            binding.lWebView.loadUrl("https://bizman.dikauri.com/signin")
        }
        binding.swEnableCopyContent.setOnCheckedChangeListener { _, isChecked ->
            binding.lWebView.setEnableCopyContent(isChecked)
        }
        binding.swEnableDarkMode.setOnCheckedChangeListener { _, isChecked ->
            try {
                binding.lWebView.setDarkMode(isChecked)
            } catch (e: Exception) {
                showSnackBarError("setOnCheckedChangeListener $e")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.lWebView.canGoBack()) {
            binding.lWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun onDetectClick() {
        logE(">>>onDetectClick")
        // detect click button submit
        if (isDetectButtonClickAsset) {
            binding.lWebView.addJavascriptInterface(object : Any() {
                @JavascriptInterface
                @Throws(java.lang.Exception::class)
                @Suppress("unused")
                fun performClick(id: String) {
                    showLongInformation("Login print order id: $id")
                }
            }, "handlePrintOrder")
        }

        if (isDetectButtonClickWeb) {
            binding.lWebView.addJavascriptInterface(object : Any() {
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
