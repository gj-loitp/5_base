package vn.loitp.app.activity.customviews.layout.autolinearlayout

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//read more
//https://github.com/AlbertGrobas/AutoLinearLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1852

@LogTag("AutoLinearLayoutActivity")
@IsFullScreen(false)
class AutoLinearLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_auto_linear
    }
}
