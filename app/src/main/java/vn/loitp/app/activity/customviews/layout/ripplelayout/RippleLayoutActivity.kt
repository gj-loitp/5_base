package vn.loitp.app.activity.customviews.layout.ripplelayout

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_ripple)
@LogTag("RippleLayoutActivity")
class RippleLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
