package vn.loitp.app.activity.function.viewdraghelpersimple

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_func_view_draghelper_simple)
@LogTag("ViewDragHelperSimpleActivity")
class ViewDragHelperSimpleActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }
}
