package vn.loitp.app.activity.customviews.textview.colortextview

import com.core.base.BaseFontActivity
import vn.loitp.app.R

class ColorTextViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_color
    }

}
