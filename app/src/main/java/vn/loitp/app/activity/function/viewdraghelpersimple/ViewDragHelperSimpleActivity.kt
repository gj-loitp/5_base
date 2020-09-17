package vn.loitp.app.activity.function.viewdraghelpersimple

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_func_view_draghelper_simple)
class ViewDragHelperSimpleActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }
}
