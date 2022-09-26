package vn.loitp.app.activity.customviews.webview.wrapContent

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_web_view_wrap_content.*
import vn.loitp.app.R


@LogTag("WebViewWrapContentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class WebViewWrapContentActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_web_view_wrap_content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
//        lActionBar.apply {
//            LUIUtil.setSafeOnClickListenerElastic(
//                view = this.ivIconLeft,
//                runnable = {
//                    onBaseBackPressed()
//                }
//            )
//            this.ivIconRight?.let {
//                LUIUtil.setSafeOnClickListenerElastic(
//                    view = it,
//                    runnable = {
//                        showShortInformation(msg = "onClickMenu", isTopAnchor = false)
//                    }
//                )
//                it.isVisible = true
//                it.setImageResource(R.drawable.ic_baseline_code_48)
//            }
//            this.viewShadow?.isVisible = true
//            this.tvTitle?.text = WebViewWrapContentActivity::class.java.simpleName
//        }


        wv.setBackgroundColor(Color.RED)
        wv.loadDataWithBaseURL(null, HtmlContent.body, "text/html", "UTF-8", null)
//        wv.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView, url: String) {
//            }
//        }
//        val viewTreeObserver: ViewTreeObserver = wv.viewTreeObserver
//        viewTreeObserver.addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                if (wv.measuredHeight > 0) {
//                    wv.viewTreeObserver.removeGlobalOnLayoutListener(this)
//                    runOnUiThread {
//                        wv.layoutParams = LinearLayoutCompat.LayoutParams(
//                            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
//                            0
//                        )
//                        wv.visibility = View.GONE
//                        wv.layoutParams = LinearLayoutCompat.LayoutParams(
//                            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
//                            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
//                        )
//                        wv.visibility = View.VISIBLE
//                    }
//                }
//            }
//        })

    }
}
