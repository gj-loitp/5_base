package vn.loitp.app.activity.function.viewdraghelpersimple

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ViewDragHelperSimpleActivity")
@IsFullScreen(false)
class ViewDragHelperSimpleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_draghelper_simple
    }
}
