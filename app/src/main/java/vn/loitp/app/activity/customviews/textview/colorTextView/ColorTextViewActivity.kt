package vn.loitp.app.activity.customviews.textview.colorTextView

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ColorTextViewActivity")
@IsFullScreen(false)
class ColorTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_color
    }
}
