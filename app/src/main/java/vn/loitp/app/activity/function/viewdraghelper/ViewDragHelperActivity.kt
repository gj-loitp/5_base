package vn.loitp.app.activity.function.viewdraghelper

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ViewDragHelperActivity")
@IsFullScreen(false)
class ViewDragHelperActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_drag_helper
    }
}
