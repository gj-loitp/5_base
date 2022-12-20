package vn.loitp.app.activity.customviews.facebookComment

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_facebook_comment.*
import vn.loitp.app.R

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
