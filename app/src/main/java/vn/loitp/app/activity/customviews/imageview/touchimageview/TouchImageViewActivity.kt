package vn.loitp.app.activity.customviews.imageview.touchimageview

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.views.imageview.touch.LTouchImageView

import loitp.basemaster.R
import vn.loitp.app.common.Constants

//note when use with glide, must have placeholder
class TouchImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lTouchImageView = findViewById<LTouchImageView>(R.id.iv)
        LImageUtil.load(activity, Constants.URL_IMG, lTouchImageView, R.mipmap.ic_launcher)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_touch_imageview
    }
}
