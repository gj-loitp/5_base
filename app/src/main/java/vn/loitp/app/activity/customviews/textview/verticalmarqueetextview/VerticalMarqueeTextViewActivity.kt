package vn.loitp.app.activity.customviews.textview.verticalmarqueetextview

import com.core.base.BaseFontActivity
import vn.loitp.app.R

class VerticalMarqueeTextViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_vertical_marque
    }

}
