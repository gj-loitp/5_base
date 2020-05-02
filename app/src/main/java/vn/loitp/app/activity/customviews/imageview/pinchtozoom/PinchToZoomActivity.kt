package vn.loitp.app.activity.customviews.imageview.pinchtozoom

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_pinch_to_zoom.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

class PinchToZoomActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LImageUtil.load(context = activity, url = Constants.URL_IMG, imageView = imageView)
        LImageUtil.setImageViewZoom(imageView)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_pinch_to_zoom
    }
}
