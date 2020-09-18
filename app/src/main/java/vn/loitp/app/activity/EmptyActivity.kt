package vn.loitp.app.activity

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LayoutId(R.layout.activity_splash_v3)
@LogTag("EmptyActivity")
class EmptyActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}
