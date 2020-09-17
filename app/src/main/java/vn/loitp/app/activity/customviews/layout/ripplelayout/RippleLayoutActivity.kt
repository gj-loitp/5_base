package vn.loitp.app.activity.customviews.layout.ripplelayout

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_ripple)
class RippleLayoutActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
