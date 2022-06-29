package vn.loitp.app.activity.function.viewdraghelpersimple

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ViewDragHelperSimpleActivity")
@IsFullScreen(false)
class ViewDragHelperSimpleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_view_draghelper_simple
    }
}
