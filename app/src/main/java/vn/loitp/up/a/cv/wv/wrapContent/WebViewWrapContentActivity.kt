package vn.loitp.up.a.cv.wv.wrapContent

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.webkit.JavascriptInterface
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AWvWrapContentBinding

@LogTag("WebViewWrapContentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class WebViewWrapContentActivity : BaseActivityFont() {
    private lateinit var binding: AWvWrapContentBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AWvWrapContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.tvTitle?.text = WebViewWrapContentActivity::class.java.simpleName
        }


        binding.wv.setBackgroundColor(Color.RED)
//        val content = HtmlContent.body
        val content = HtmlContent.body.replace("height:", "height_:")
        binding.wv.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null)

        binding.wv.settings.javaScriptEnabled = true
//        wv.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//            }
//        }

        binding.wv.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            @Suppress("unused")
            fun performClick(string: String?) {
                showLongWarning("click button close")
            }
        }, "onClickClose")

        binding.wv.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            @Suppress("unused")
            fun performClick(string: String?) {
                showLongWarning("click body")
            }
        }, "onClickBody")
    }
}