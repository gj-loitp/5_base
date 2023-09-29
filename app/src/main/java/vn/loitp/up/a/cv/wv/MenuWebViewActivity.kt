package vn.loitp.up.a.cv.wv

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AMenuWvBinding
import vn.loitp.up.a.cv.wv.l.LWebViewActivity
import com.loitp.views.wv.superWebView.SuperWebViewActivity
import vn.loitp.up.a.cv.wv.wrapContent.WebViewWrapContentActivity

@LogTag("MenuWebViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuWebViewActivity : BaseActivityFont() {

    private lateinit var binding: AMenuWvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuWvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuWebViewActivity::class.java.simpleName
        }
        binding.btLWebView.setSafeOnClickListener {
            launchActivity(LWebViewActivity::class.java)
        }
        binding.btSuperWebView.setSafeOnClickListener {
            launchActivity(SuperWebViewActivity::class.java, data = {
                it.putExtra(SuperWebViewActivity.KEY_URL, "https://github.com/gj-loitp334envjdfv")
            })
        }
        binding.btWebViewWrapContent.setSafeOnClickListener {
            launchActivity(WebViewWrapContentActivity::class.java)
        }
    }
}
