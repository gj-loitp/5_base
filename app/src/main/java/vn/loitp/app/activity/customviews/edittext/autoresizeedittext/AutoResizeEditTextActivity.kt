package vn.loitp.app.activity.customviews.edittext.autoresizeedittext

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_editext_autoresize)
@LogTag("AutoResizeEditTextActivity")
class AutoResizeEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
