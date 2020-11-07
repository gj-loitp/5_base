package vn.loitp.app.activity.customviews.edittext.autoresizeedittext

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("AutoResizeEditTextActivity")
@IsFullScreen(false)
class AutoResizeEditTextActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_autoresize
    }
}
