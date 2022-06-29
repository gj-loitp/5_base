package vn.loitp.app.activity.customviews.textview.zoomtextview

import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ZoomTextViewActivity")
@IsFullScreen(false)
class ZoomTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_zoom
    }
}
