package vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/Cutta/ContinuousScrollableImageView

@LogTag("ContinuousScrollableImageViewActivity")
@IsFullScreen(false)
class ContinuousScrollableImageViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_continuousscrollable
    }
}
