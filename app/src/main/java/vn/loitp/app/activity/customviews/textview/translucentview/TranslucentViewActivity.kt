package vn.loitp.app.activity.customviews.textview.translucentview

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("TranslucentViewActivity")
@IsFullScreen(false)
class TranslucentViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_translucent
    }
}
