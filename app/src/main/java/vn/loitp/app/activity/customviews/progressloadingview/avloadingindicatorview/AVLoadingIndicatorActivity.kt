package vn.loitp.app.activity.customviews.progressloadingview.avloadingindicatorview

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_progress_avloading_indicator)
@LogTag("AVLoadingIndicatorActivity")
class AVLoadingIndicatorActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
