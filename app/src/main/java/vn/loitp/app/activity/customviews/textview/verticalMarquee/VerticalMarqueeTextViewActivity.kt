package vn.loitp.app.activity.customviews.textview.verticalMarquee

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("VerticalMarqueeTextViewActivity")
@IsFullScreen(false)
class VerticalMarqueeTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_vertical_marque
    }
}
