package vn.loitp.app.activity.customviews.button.shinebutton

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ShineButtonActivity")
@IsFullScreen(false)
class ShineButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_shine
    }
}
