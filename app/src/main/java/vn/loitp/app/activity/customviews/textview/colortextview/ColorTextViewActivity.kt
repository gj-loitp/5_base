package vn.loitp.app.activity.customviews.textview.colortextview

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_color)
@LogTag("ColorTextViewActivity")
class ColorTextViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }
}
