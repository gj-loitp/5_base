package vn.loitp.app.activity.customviews.facebookcomment

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import kotlinx.android.synthetic.main.activity_fb_cmt.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_fb_cmt)
@LogTag("FacebookCommentActivity")
@IsFullScreen(false)
class FacebookCommentActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt.setOnClickListener { _ ->
            LSocialUtil.openFacebookComment(context = this, url = "http://truyentranhtuan.com/one-piece-chuong-907/", adUnitId = getString(R.string.str_b))
        }
    }

}
