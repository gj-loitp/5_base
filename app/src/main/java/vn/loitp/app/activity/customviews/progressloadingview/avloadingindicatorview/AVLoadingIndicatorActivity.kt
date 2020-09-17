package vn.loitp.app.activity.customviews.progressloadingview.avloadingindicatorview

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_progress_avloading_indicator)
class AVLoadingIndicatorActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
