package vn.loitp.app.activity.customviews.layout.roundablelayout

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_roundable)
@LogTag("RoundableLayoutActivity")
class RoundableLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
