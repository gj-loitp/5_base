package vn.loitp.app.activity.customviews.progressloadingview.avloadingindicatorview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("AVLoadingIndicatorActivity")
@IsFullScreen(false)
class AVLoadingIndicatorActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_progress_avloading_indicator
    }
}
