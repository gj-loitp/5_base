package vn.loitp.app.activity.customviews.imageview.pinchtozoom

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_pinch_to_zoom.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("PinchToZoomActivity")
@IsFullScreen(false)
class PinchToZoomActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_pinch_to_zoom
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LImageUtil.load(context = this, any = Constants.URL_IMG, imageView = imageView)
    }

}
