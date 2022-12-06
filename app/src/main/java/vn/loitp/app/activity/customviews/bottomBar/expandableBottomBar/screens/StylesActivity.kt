package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens

import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("StylesActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class StylesActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_styles
    }
}
