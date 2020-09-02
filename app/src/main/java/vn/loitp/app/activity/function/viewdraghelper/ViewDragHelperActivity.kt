package vn.loitp.app.activity.function.viewdraghelper

import com.core.base.BaseFontActivity
import vn.loitp.app.R

class ViewDragHelperActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_drag_helper
    }
}
