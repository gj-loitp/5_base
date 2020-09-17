package vn.loitp.app.activity.customviews.button.shinebutton

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_button_shine)
class ShineButtonActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
