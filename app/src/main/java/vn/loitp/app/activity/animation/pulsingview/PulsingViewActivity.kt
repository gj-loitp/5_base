package vn.loitp.app.activity.animation.pulsingview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("PulsingViewActivity")
@IsFullScreen(false)
class PulsingViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pulsing_view
    }
}
