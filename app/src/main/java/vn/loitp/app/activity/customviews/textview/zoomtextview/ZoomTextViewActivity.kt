package vn.loitp.app.activity.customviews.textview.zoomtextview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("ZoomTextViewActivity")
@IsFullScreen(false)
class ZoomTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_zoom
    }
}
