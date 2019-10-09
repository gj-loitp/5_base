package vn.loitp.app.activity.customviews.imageview.pinchtozoom

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_pinch_to_zoom.*
import loitp.basemaster.R
import vn.loitp.app.common.Constants

class PinchToZoomActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LImageUtil.load(context = activity, url = Constants.URL_IMG, imageView = iv)
        LUIUtil.setImageViewZoom(iv)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pinch_to_zoom
    }
}
