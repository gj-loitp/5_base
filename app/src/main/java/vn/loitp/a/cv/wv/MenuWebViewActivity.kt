package vn.loitp.a.cv.wv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_wv.*
import vn.loitp.R
import vn.loitp.a.cv.wv.l.LWebViewActivity
import vn.loitp.a.cv.wv.wrapContent.WebViewWrapContentActivity

@LogTag("MenuWebViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuWebViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_wv
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
        btLWebView.setSafeOnClickListener {
            launchActivity(LWebViewActivity::class.java)
        }
        btWebViewWrapContent.setSafeOnClickListener {
            launchActivity(WebViewWrapContentActivity::class.java)
        }
    }
}
