package vn.loitp.app.activity.animation.pulsingView

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("PulsingViewActivity")
@IsFullScreen(false)
class PulsingViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pulsing_view
    }
}
