package vn.loitp.app.activity.customviews.textview.strokedtextview

import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/melihaksoy/StrokedTextView

@LayoutId(R.layout.activity_text_view_stroked)
class StrokedTextViewActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
