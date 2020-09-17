package vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/Cutta/ContinuousScrollableImageView

@LayoutId(R.layout.activity_imageview_continuousscrollable)
class ContinuousScrollableImageViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}