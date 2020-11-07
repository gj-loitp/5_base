package vn.loitp.app.activity.customviews.edittext.biuedittext

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//guide https://github.com/xujinyang/BiuEditText

@LogTag("BiuEditTextActivity")
@IsFullScreen(false)
class BiuEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_biu
    }
}
