package vn.loitp.app.activity.customviews.imageview.touchimageview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_touch_imageview.*
import loitp.basemaster.R
import vn.loitp.app.common.Constants

class TouchImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //note when use with glide, must have placeholder
        LImageUtil.load(context = activity, url = Constants.URL_IMG, imageView = lTouchImageView, resPlaceHolder = R.color.colorPrimary)
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
