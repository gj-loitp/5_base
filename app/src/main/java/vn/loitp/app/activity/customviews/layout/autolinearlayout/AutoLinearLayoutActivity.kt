package vn.loitp.app.activity.customviews.layout.autolinearlayout

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//read more
//https://github.com/AlbertGrobas/AutoLinearLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1852

@LayoutId(R.layout.activity_layout_auto_linear)
@LogTag("AutoLinearLayoutActivity")
class AutoLinearLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
