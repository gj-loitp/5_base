package vn.loitp.app.a.customviews.facebookComment

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_facebook_comment.*
import vn.loitp.R

@LogTag("FacebookCommentActivity")
@IsFullScreen(false)
class FacebookCommentActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_facebook_comment
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
            this.tvTitle?.text = FacebookCommentActivity::class.java.simpleName
        }
        bt.setSafeOnClickListener {
            LSocialUtil.openFacebookComment(
                context = this,
                url = "http://truyentuan.com/one-piece-chuong-907/",
            )
        }
    }
}
