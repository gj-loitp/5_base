package vn.loitp.app.activity.customviews.facebookcomment

import android.os.Bundle
import android.view.View

import com.core.base.BaseFontActivity
import com.core.utilities.LSocialUtil

import loitp.basemaster.R

class FacebookCommentActivity : BaseFontActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<View>(R.id.bt).setOnClickListener { v -> LSocialUtil.openFacebookComment(activity, "http://truyentranhtuan.com/one-piece-chuong-907/", getString(R.string.str_b)) }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fb_cmt
    }
}
