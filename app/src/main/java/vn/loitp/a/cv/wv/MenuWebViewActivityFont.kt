package vn.loitp.a.cv.wv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_wv.*
import vn.loitp.R
import vn.loitp.a.cv.wv.l.LWebViewActivityFont
import vn.loitp.a.cv.wv.wrapContent.WebViewWrapContentActivityFont

@LogTag("MenuWebViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuWebViewActivityFont : BaseActivityFont() {

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
            this.tvTitle?.text = MenuWebViewActivityFont::class.java.simpleName
        }
        btLWebView.setSafeOnClickListener {
            launchActivity(LWebViewActivityFont::class.java)
        }
        btWebViewWrapContent.setSafeOnClickListener {
            launchActivity(WebViewWrapContentActivityFont::class.java)
        }
    }
}
