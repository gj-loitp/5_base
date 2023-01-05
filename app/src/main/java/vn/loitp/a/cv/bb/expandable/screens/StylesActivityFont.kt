package vn.loitp.a.cv.bb.expandable.screens

import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import vn.loitp.R

@LogTag("StylesActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class StylesActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_styles
    }
}
