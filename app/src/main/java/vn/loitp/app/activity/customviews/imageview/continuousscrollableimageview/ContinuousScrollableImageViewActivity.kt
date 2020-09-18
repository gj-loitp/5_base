package vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/Cutta/ContinuousScrollableImageView

@LayoutId(R.layout.activity_imageview_continuousscrollable)
@LogTag("ContinuousScrollableImageViewActivity")
class ContinuousScrollableImageViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}