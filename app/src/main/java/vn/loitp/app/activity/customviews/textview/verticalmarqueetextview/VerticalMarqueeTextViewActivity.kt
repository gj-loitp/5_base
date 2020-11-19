package vn.loitp.app.activity.customviews.textview.verticalmarqueetextview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("VerticalMarqueeTextViewActivity")
@IsFullScreen(false)
class VerticalMarqueeTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_vertical_marque
    }
}
