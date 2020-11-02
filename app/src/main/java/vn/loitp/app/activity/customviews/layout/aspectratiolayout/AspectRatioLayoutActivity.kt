package vn.loitp.app.activity.customviews.layout.aspectratiolayout

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("AspectRatioLayoutActivity")
@IsFullScreen(false)
class AspectRatioLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_aspect_ratio
    }
}
