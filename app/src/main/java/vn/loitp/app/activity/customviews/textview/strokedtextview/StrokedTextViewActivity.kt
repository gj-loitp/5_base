package vn.loitp.app.activity.customviews.textview.strokedtextview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

// https://github.com/melihaksoy/StrokedTextView

@LogTag("StrokedTextViewActivity")
@IsFullScreen(false)
class StrokedTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_stroked
    }
}
