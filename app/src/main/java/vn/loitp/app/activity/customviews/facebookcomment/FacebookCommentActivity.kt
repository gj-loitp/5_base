package vn.loitp.app.activity.customviews.facebookcomment

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil
import kotlinx.android.synthetic.main.activity_fb_cmt.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_fb_cmt)
class FacebookCommentActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt.setOnClickListener { _ ->
            LSocialUtil.openFacebookComment(context = activity, url = "http://truyentranhtuan.com/one-piece-chuong-907/", adUnitId = getString(R.string.str_b))
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
