package vn.loitp.app.activity.customviews.webview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_custom_view.lActionBar
import kotlinx.android.synthetic.main.activity_menu_webview.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.webview.lwebView.LWebViewActivity
import vn.loitp.app.activity.customviews.webview.wrapContent.WebViewWrapContentActivity

@LogTag("MenuWebViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuWebViewActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_webview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuWebViewActivity::class.java.simpleName
        }
        btLWebView.setOnClickListener(this)
        btWebViewWrapContent.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btLWebView -> {
                Intent(this, LWebViewActivity::class.java)
            }
            btWebViewWrapContent -> {
                Intent(this, WebViewWrapContentActivity::class.java)
            }
            else -> null
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
