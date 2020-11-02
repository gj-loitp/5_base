package vn.loitp.app.activity.customviews.layout.ripplelayout

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("RippleLayoutActivity")
@IsFullScreen(false)
class RippleLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_ripple
    }
}
