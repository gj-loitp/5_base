package vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("FabFollowWidgetActivity")
@IsFullScreen(false)
class FabFollowWidgetActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fab_follow_widget
    }
}
