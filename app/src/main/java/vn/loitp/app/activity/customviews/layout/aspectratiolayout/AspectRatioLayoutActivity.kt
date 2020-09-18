package vn.loitp.app.activity.customviews.layout.aspectratiolayout

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_aspect_ratio)
@LogTag("AspectRatioLayoutActivity")
class AspectRatioLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }
}
