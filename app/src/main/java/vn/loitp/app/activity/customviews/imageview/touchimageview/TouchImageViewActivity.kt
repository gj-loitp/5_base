package vn.loitp.app.activity.customviews.imageview.touchimageview

import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_touch.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LayoutId(R.layout.activity_imageview_touch)
@LogTag("TouchImageViewActivity")
class TouchImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //note when use with glide, must have placeholder
        LImageUtil.load(context = activity, url = Constants.URL_IMG, imageView = lTouchImageView, resPlaceHolder = R.color.colorPrimary)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
