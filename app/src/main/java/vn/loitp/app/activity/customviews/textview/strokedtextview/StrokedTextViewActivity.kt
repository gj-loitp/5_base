package vn.loitp.app.activity.customviews.textview.strokedtextview

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/melihaksoy/StrokedTextView

@LayoutId(R.layout.activity_text_view_stroked)
@LogTag("StrokedTextViewActivity")
class StrokedTextViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
