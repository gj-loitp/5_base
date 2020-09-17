package vn.loitp.app.activity.customviews.layout.constraintlayout.fabfollowswiidget

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_fab_follow_widget)
class FabFollowWidgetActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
