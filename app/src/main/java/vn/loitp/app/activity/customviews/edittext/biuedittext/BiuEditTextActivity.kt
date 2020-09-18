package vn.loitp.app.activity.customviews.edittext.biuedittext

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//guide https://github.com/xujinyang/BiuEditText

@LayoutId(R.layout.activity_editext_biu)
@LogTag("BiuEditTextActivity")
class BiuEditTextActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
