package com.loitp.views.wv.superWebView

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.databinding.ASuperWvBinding

@LogTag("EmptyActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SuperWebViewActivity : BaseActivityFont() {
    companion object {
        const val KEY_URL = "KEY_URL"
    }

    private var currentWebsite: String = "https://www.facebook.com/loitp93/"
    private lateinit var binding: ASuperWvBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ASuperWvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentWebsite = intent?.getStringExtra(KEY_URL) ?: ""

        setupViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBack()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/roozbehzarei/SuperWebView"
                    )
                })
                isVisible = true
                setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = SuperWebViewActivity::class.java.simpleName
        }

        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.webChromeClient = MyWebChromeClient()
        with(binding.webView.settings) {
            // Tell the WebView to enable JavaScript execution
            javaScriptEnabled = true
            // Enable DOM storage API
            domStorageEnabled = false
            // Disable support for zooming using webView's on-screen zoom controls and gestures
            setSupportZoom(false)
        }
        // If dark theme is turned on, automatically render all web contents using a dark theme
        if (WebViewFeature.isFeatureSupported(WebViewFeature.ALGORITHMIC_DARKENING)) {
            when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    WebSettingsCompat.setAlgorithmicDarkeningAllowed(binding.webView.settings, true)
                }

                Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                    WebSettingsCompat.setAlgorithmicDarkeningAllowed(binding.webView.settings, false)
                }
            }
        }

        binding.webView.loadUrl(currentWebsite)

        /**
         * Theme Swipe-to-refresh layout
         */
        val spinnerTypedValue = TypedValue()
        theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimary, spinnerTypedValue, true
        )

        val backgroundTypedValue = TypedValue()
        theme.resolveAttribute(
            com.google.android.material.R.attr.colorPrimaryContainer, backgroundTypedValue, true
        )
//        val backgroundColor = backgroundTypedValue.resourceId
//        binding.srl.setProgressBackgroundColorSchemeResource(backgroundColor)

        binding.webView.viewTreeObserver.addOnScrollChangedListener {
            binding.root.isEnabled = binding.webView.scrollY == 0
        }

        /**
         * If there's no web page history, close the application
         */
        val mCallback = onBackPressedDispatcher.addCallback(this) {
            onBack()
        }
        mCallback.isEnabled = true
    }

    private fun onBack() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            finish()
        }
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?, request: WebResourceRequest?,
        ): Boolean {
            if (request?.url.toString().contains(currentWebsite)) {
                return false
            }
            Intent(Intent.ACTION_VIEW, request?.url).apply {
                startActivity(this)
            }
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            binding.webView.visibility = View.VISIBLE
            binding.errorLayout.visibility = View.GONE
            binding.progressIndicator.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            binding.progressIndicator.visibility = View.INVISIBLE
        }

        override fun onReceivedError(
            view: WebView?, request: WebResourceRequest?, error: WebResourceError?,
        ) {
            super.onReceivedError(view, request, error)
            binding.webView.visibility = View.GONE
            binding.errorLayout.visibility = View.VISIBLE
            binding.root.isEnabled = false
            binding.retryButton.setOnClickListener {
                if (view?.url.isNullOrEmpty()) {
                    view?.loadUrl(currentWebsite)
                } else {
                    view?.reload()
                }
            }
        }

    }

    /**
     * Update the progress bar when loading a webpage
     */
    private inner class MyWebChromeClient : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            binding.progressIndicator.progress = newProgress
        }
    }
}
