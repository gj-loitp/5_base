package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens

import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("StylesActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class StylesActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_styles
    }
}
