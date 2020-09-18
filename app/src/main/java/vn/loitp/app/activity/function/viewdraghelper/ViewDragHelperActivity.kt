package vn.loitp.app.activity.function.viewdraghelper

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_func_view_drag_helper)
@LogTag("ViewDragHelperActivity")
class ViewDragHelperActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
