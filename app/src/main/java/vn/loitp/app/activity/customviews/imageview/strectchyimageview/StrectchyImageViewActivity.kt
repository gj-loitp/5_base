package vn.loitp.app.activity.customviews.imageview.strectchyimageview

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.views.imageview.strectchy.LStretchyImageView

import loitp.basemaster.R
import vn.loitp.app.common.Constants

class StrectchyImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lStretchyImageView = findViewById<LStretchyImageView>(R.id.iv)
        LImageUtil.load(activity, Constants.URL_IMG_LONG, lStretchyImageView)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_strectchy_imageview
    }
}
